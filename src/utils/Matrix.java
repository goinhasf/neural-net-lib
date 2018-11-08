package utils;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

	private Double[][] matrix;
	private int numOfRows;
	private int numOfCols;

	/**
	 * Default Constructor to create an empty matrix of a specified dimension.
	 * 
	 * @param numOfRows
	 *            The number of rows of the matrix.
	 * @param numOfCols
	 *            The number of columns of the matrix.
	 */

	public Matrix(int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;

		matrix = new Double[numOfRows][numOfCols];
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				matrix[i][j] = 0d;
			}
		}

	}

	/**
	 * Constructor used to initalize a filled array.
	 *
	 * @param matrix
	 *            A 2D array containing the matrix.
	 * @param numOfRows
	 *            The number of rows of the matrix.
	 * @param numOfCols
	 *            The number of columns of the matrix.
	 * 
	 */
	public Matrix(Double[][] matrix, int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		this.matrix = matrix;
	}

	/**
	 * Constructs a matrix from a list of rows and a list of columns.
	 * 
	 * @param row
	 *            A list of row values.
	 * @param cols
	 *            A list of column values.
	 * 
	 */
	public Matrix(List<Double> rows, List<Double> cols) {

		this.numOfRows = rows.size();
		this.numOfCols = cols.size();

		List<Double> all = cols;
		all.addAll(rows);

		matrix = new Double[numOfRows][numOfCols];

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				matrix[i][j] = all.get(i);
			}
		}

	}

	/**
	 * Adds two matrices of type double.
	 * 
	 * @param m
	 *            The matrix to add to the current matrix.
	 * @return Returns the product of the sum of matrix m with the current matrix.
	 * @throws Exception
	 *             Throws an exception when the matrices don't have the same size.
	 */
	public static Matrix add(Matrix m1, Matrix m2) throws Exception {

		if (m1.getNumOfCols() != m2.getNumOfCols() || m1.getNumOfRows() != m2.getNumOfRows()) {
			throw new Exception("Matrices don't have the same size");
		}

		Double[][] matrixArray = new Double[m1.getNumOfRows()][m1.getNumOfCols()];

		for (int i = 0; i < m1.getNumOfRows(); i++) {
			for (int j = 0; j < m1.getNumOfCols(); j++) {
				matrixArray[i][j] = m1.get(i, j) + m2.get(i, j);
			}
		}

		return new Matrix(matrixArray, m1.getNumOfRows(), m1.getNumOfCols());

	}

	/**
	 * Adds two matrices together.
	 * 
	 * @param m1
	 *            Matrix to add.
	 * @param m2
	 *            The other matrix to add.
	 * @return Returns a new matrix of size m1.Rows x m2.Cols
	 * @throws IllegalArgumentException
	 *             If the rows and columns size of m1 and m2 do not match.
	 */
	public static Matrix mult(Matrix m1, Matrix m2) {

		int aRows = m1.getNumOfRows();
		int aColumns = m1.getNumOfCols();
		int bRows = m2.getNumOfRows();
		int bColumns = m2.getNumOfCols();

		if (aColumns != bRows) {
			throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
		}

		Double[][] C = new Double[aRows][bColumns];
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < bColumns; j++) {
				C[i][j] = 0.00000;
			}
		}

		for (int i = 0; i < aRows; i++) { // aRow
			for (int j = 0; j < bColumns; j++) { // bColumn
				for (int k = 0; k < aColumns; k++) { // aColumn
					C[i][j] += m1.get(i, k) * m2.get(k, j);
				}
			}
		}

		return new Matrix(C, aRows, bColumns);
	}

	/**
	 * Returns the element of the matrix at row = i col = j
	 * 
	 * @param i
	 *            The row index.
	 * @param j
	 *            The col index.
	 * @return Returns the element of the matrix at row = i col = j
	 */
	public Double get(int i, int j) {

		return matrix[i][j];
	}

	/**
	 * Returns a list of values at a given column.
	 * 
	 * @param colIndex
	 *            The index of the column to get the values.
	 * @return Returns a list of values at a given column index.
	 */
	public List<Double> getRowValues(int colIndex) {

		if (colIndex < 0 || colIndex > numOfRows - 1) {
			throw new IndexOutOfBoundsException();
		}

		List<Double> rows = new ArrayList<>();

		for (int i = 0; i < numOfRows; i++) {
			rows.add(matrix[i][colIndex]);
		}

		return rows;
	}

	/**
	 * Returns a list of values at a given row.
	 * 
	 * @param rowIndex
	 *            The index of the row to get the values.
	 * @return Returns a list of values at a given row index.
	 * 
	 */
	public List<Double> getColValues(int rowIndex) {

		if (rowIndex < 0 || rowIndex > numOfRows - 1) {
			throw new IndexOutOfBoundsException();
		}

		List<Double> cols = new ArrayList<>();

		for (int i = 0; i < numOfCols; i++) {
			cols.add(matrix[rowIndex][i]);
		}

		return cols;
	}

	/**
	 * 
	 * @return Returns the number of rows of the matrix.
	 */
	public int getNumOfRows() {
		return numOfRows;
	}

	/**
	 * 
	 * @return Returns the number of cols of the matrix.
	 */
	public int getNumOfCols() {
		return numOfCols;
	}

}
