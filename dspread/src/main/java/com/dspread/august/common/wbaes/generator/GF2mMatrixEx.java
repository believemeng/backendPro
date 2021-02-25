/*
 * Copyright (c) 2014, Dusan (Ph4r05) Klinec, Petr Svenda
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holders nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.dspread.august.common.wbaes.generator;

import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.IntUtils;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.Vector;

import java.util.Arrays;

/**
 * This class describes some operations with matrices over finite field <i>GF(2<sup>m</sup>)</i>
 * with small <i>m</i> (1< m <32).
 *
 * @see Matrix
 * @source bouncycastle.org
 */
public class GF2mMatrixEx extends Matrix {
    /**
     * finite field GF(2^m)
     */
    protected GF2mField field;

    /**
     * For the matrix representation the array of type int[][] is used, thus
     * every element of the array keeps one element of the matrix (element from
     * finite field GF(2^m))
     */
    protected int[][] matrix;

    /**
     * Constructor.
     *
     * @param field a finite field GF(2^m)
     * @param enc   byte[] matrix in byte array form
     */
    public GF2mMatrixEx(GF2mField field, int m, int n)
    {

        this.field = field;

        // decode matrix
        int d = 8;
        int count = 1;
        while (field.getDegree() > d)
        {
            count++;
            d += 8;
        }

        this.numRows = m;
        this.numColumns = n;
        this.matrix = new int[this.numRows][this.numColumns];
        for(int i=0; i<numRows; i++){
            Arrays.fill(this.matrix[i], 0);
        }
    }
    
    /**
     * Constructor.
     *
     * @param field a finite field GF(2^m)
     * @param enc   byte[] matrix in byte array form
     */
    public GF2mMatrixEx(GF2mField field, byte[] enc)
    {

        this.field = field;

        // decode matrix
        int d = 8;
        int count = 1;
        while (field.getDegree() > d)
        {
            count++;
            d += 8;
        }

        if (enc.length < 5)
        {
            throw new IllegalArgumentException(
                " Error: given array is not encoded matrix over GF(2^m)");
        }

        this.numRows = ((enc[3] & 0xff) << 24) ^ ((enc[2] & 0xff) << 16)
            ^ ((enc[1] & 0xff) << 8) ^ (enc[0] & 0xff);

        int n = count * this.numRows;

        if ((this.numRows <= 0) || (((enc.length - 4) % n) != 0))
        {
            throw new IllegalArgumentException(
                " Error: given array is not encoded matrix over GF(2^m)");
        }

        this.numColumns = (enc.length - 4) / n;

        matrix = new int[this.numRows][this.numColumns];
        count = 4;
        for (int i = 0; i < this.numRows; i++)
        {
            for (int j = 0; j < this.numColumns; j++)
            {
                for (int jj = 0; jj < d; jj += 8)
                {
                    matrix[i][j] ^= (enc[count++] & 0x000000ff) << jj;
                }
                if (!this.field.isElementOfThisField(matrix[i][j]))
                {
                    throw new IllegalArgumentException(
                        " Error: given array is not encoded matrix over GF(2^m)");
                }
            }
        }
    }

    /**
     * Copy constructor.
     *
     * @param other another {@link GF2mMatrixEx}
     */
    public GF2mMatrixEx(GF2mMatrixEx other)
    {
        numRows = other.numRows;
        numColumns = other.numColumns;
        field = other.field;
        matrix = new int[numRows][];
        for (int i = 0; i < numRows; i++)
        {
            matrix[i] = IntUtils.clone(other.matrix[i]);
        }
    }

    /**
     * Constructor.
     *
     * @param field  a finite field GF(2^m)
     * @param matrix the matrix as int array. Only the reference is copied.
     */
    protected GF2mMatrixEx(GF2mField field, int[][] matrix)
    {
        this.field = field;
        this.matrix = matrix;
        numRows = matrix.length;
        numColumns = matrix[0].length;
    }

    /**
     * @return a byte array encoding of this matrix
     */
    public byte[] getEncoded()
    {
        int d = 8;
        int count = 1;
        while (field.getDegree() > d)
        {
            count++;
            d += 8;
        }

        byte[] bf = new byte[this.numRows * this.numColumns * count + 4];
        bf[0] = (byte)(this.numRows & 0xff);
        bf[1] = (byte)((this.numRows >>> 8) & 0xff);
        bf[2] = (byte)((this.numRows >>> 16) & 0xff);
        bf[3] = (byte)((this.numRows >>> 24) & 0xff);

        count = 4;
        for (int i = 0; i < this.numRows; i++)
        {
            for (int j = 0; j < this.numColumns; j++)
            {
                for (int jj = 0; jj < d; jj += 8)
                {
                    bf[count++] = (byte)(matrix[i][j] >>> jj);
                }
            }
        }

        return bf;
    }

    /**
     * Check if this is the zero matrix (i.e., all entries are zero).
     *
     * @return <tt>true</tt> if this is the zero matrix
     */
    public boolean isZero()
    {
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                if (matrix[i][j] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compute the inverse of this matrix.
     *
     * @return the inverse of this matrix (newly created).
     */
    public Matrix computeInverse()
    {
        if (numRows != numColumns)
        {
            throw new ArithmeticException("Matrix is not invertible.");
        }

        // clone this matrix
        int[][] tmpMatrix = new int[numRows][numRows];
        for (int i = numRows - 1; i >= 0; i--)
        {
            tmpMatrix[i] = IntUtils.clone(matrix[i]);
        }

        // initialize inverse matrix as unit matrix
        int[][] invMatrix = new int[numRows][numRows];
        for (int i = numRows - 1; i >= 0; i--)
        {
            invMatrix[i][i] = 1;
        }

        // simultaneously compute Gaussian reduction of tmpMatrix and unit
        // matrix
        for (int i = 0; i < numRows; i++)
        {
            // if diagonal element is zero
            if (tmpMatrix[i][i] == 0)
            {
                boolean foundNonZero = false;
                // find a non-zero element in the same column
                for (int j = i + 1; j < numRows; j++)
                {
                    if (tmpMatrix[j][i] != 0)
                    {
                        // found it, swap rows ...
                        foundNonZero = true;
                        swapColumns(tmpMatrix, i, j);
                        swapColumns(invMatrix, i, j);
                        // ... and quit searching
                        j = numRows;
                        continue;
                    }
                }
                // if no non-zero element was found
                if (!foundNonZero)
                {
                    // the matrix is not invertible
                    throw new ArithmeticException("Matrix is not invertible.");
                }
            }

            // normalize i-th row
            int coef = tmpMatrix[i][i];
            int invCoef = field.inverse(coef);
            multRowWithElementThis(tmpMatrix[i], invCoef);
            multRowWithElementThis(invMatrix[i], invCoef);

            // normalize all other rows
            for (int j = 0; j < numRows; j++)
            {
                if (j != i)
                {
                    coef = tmpMatrix[j][i];
                    if (coef != 0)
                    {
                        int[] tmpRow = multRowWithElement(tmpMatrix[i], coef);
                        int[] tmpInvRow = multRowWithElement(invMatrix[i], coef);
                        addToRow(tmpRow, tmpMatrix[j]);
                        addToRow(tmpInvRow, invMatrix[j]);
                    }
                }
            }
        }

        return new GF2mMatrixEx(field, invMatrix);
    }

    private static void swapColumns(int[][] matrix, int first, int second)
    {
        int[] tmp = matrix[first];
        matrix[first] = matrix[second];
        matrix[second] = tmp;
    }

    private void multRowWithElementThis(int[] row, int element)
    {
        for (int i = row.length - 1; i >= 0; i--)
        {
            row[i] = field.mult(row[i], element);
        }
    }

    private int[] multRowWithElement(int[] row, int element)
    {
        int[] result = new int[row.length];
        for (int i = row.length - 1; i >= 0; i--)
        {
            result[i] = field.mult(row[i], element);
        }
        return result;
    }

    /**
     * Add one row to another.
     *
     * @param fromRow the addend
     * @param toRow   the row to add to
     */
    private void addToRow(int[] fromRow, int[] toRow)
    {
        for (int i = toRow.length - 1; i >= 0; i--)
        {
            toRow[i] = field.add(fromRow[i], toRow[i]);
        }
    }

    public Matrix rightMultiply(Matrix a)
    {
        throw new RuntimeException("Not implemented.");
    }
    
    public int get(int i, int j){
        return matrix[i][j];
    }
    
    public void set(int i, int j, int c){
        matrix[i][j] = c;
    }

    public int[][] getMatrix() {
        return matrix;
    }
    
    public GF2mMatrixEx rightMultiply(GF2mMatrixEx a)
    {
        if (this.getNumColumns() != a.getNumRows()){
            throw new IllegalArgumentException("Dimension mismatch");
        }
        
        final int[][] ai = a.getMatrix();
        final int columnsInA = a.getNumColumns();
        
        GF2mMatrixEx res = new GF2mMatrixEx(this.field, this.numRows, columnsInA);
        final int[][] ri = res.getMatrix();
        
        for (int i = 0; i < this.numRows; i++) {
           for (int j = 0; j < columnsInA; j++) {
               for (int k = 0; k < this.numColumns; k++) {
                   ri[i][j] = field.add(ri[i][j], field.mult(matrix[i][k], ai[k][j]));
               }
           }
        }
        return res;
    }

    public Matrix rightMultiply(Permutation perm)
    {
        throw new RuntimeException("Not implemented.");
    }

    public Vector leftMultiply(Vector vector)
    {
        throw new RuntimeException("Not implemented.");
    }

    public Vector rightMultiply(Vector vector)
    {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Checks if given object is equal to this matrix. The method returns false
     * whenever the given object is not a matrix over GF(2^m).
     *
     * @param other object
     * @return true or false
     */
    @Override
    public boolean equals(Object other)
    {

        if (other == null || !(other instanceof GF2mMatrixEx))
        {
            return false;
        }

        GF2mMatrixEx otherMatrix = (GF2mMatrixEx)other;

        if ((!this.field.equals(otherMatrix.field))
            || (otherMatrix.numRows != this.numColumns)
            || (otherMatrix.numColumns != this.numColumns))
        {
            return false;
        }

        for (int i = 0; i < this.numRows; i++)
        {
            for (int j = 0; j < this.numColumns; j++)
            {
                if (this.matrix[i][j] != otherMatrix.matrix[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = (this.field.hashCode() * 31 + numRows) * 31 + numColumns;
        for (int i = 0; i < this.numRows; i++)
        {
            for (int j = 0; j < this.numColumns; j++)
            {
                hash = hash * 31 + matrix[i][j];
            }
        }
        return hash;
    }

    public String toString()
    {
        String str = this.numRows + " x " + this.numColumns + " Matrix over "
            + this.field.toString() + ": \n";

        for (int i = 0; i < this.numRows; i++)
        {
            for (int j = 0; j < this.numColumns; j++)
            {
                str = str + this.field.elementToStr(matrix[i][j]) + " : ";
            }
            str = str + "\n";
        }

        return str;
    }

}