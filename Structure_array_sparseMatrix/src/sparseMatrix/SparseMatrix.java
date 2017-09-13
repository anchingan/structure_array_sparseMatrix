package sparseMatrix;

import java.util.Arrays;

public class SparseMatrix {
	
	private Term[] terms;
	
	public SparseMatrix(int r, int c) {
		terms = new Term[2];
		terms[0] = new Term(r, c, 0);
	}
	
	public int add(int r, int c, int value) {
		if (terms[0].value + 1 >= terms.length)
			this.resize();
		if (r >= 0 && c >= 0 && value != 0) {
			terms[++terms[0].value] = new Term(r, c, value);
			return terms[0].value;
		}
		return -1;
	}
	
	private void addTail() {
		int index = this.add(terms[0].row + 1, 0, 5);
		terms[index].value = 0;
	}
	
	private void delTail() {
		terms[terms[0].value + 1] = null;
	}
	
	public void create(int[][] arrayInt) {
		if (arrayInt.length != terms[0].row)
			;
		else {
			for (int i = 0; i < terms[0].row; i++) {
				for (int j = 0; j < terms[0].col; j++) {
					if (arrayInt[i][j] != 0)
						this.add(i, j, arrayInt[i][j]);
				}
			}
		}
		terms = Arrays.copyOf(terms, terms[0].value + 1);
	}
	
	public void print() {
		System.out.println(terms[0].row + " " + terms[0].col);
		int index = 1;
		for (int i = 0; i < terms[0].row; i++) {
			if (i != 0)
				System.out.println("");
			for (int j = 0; j < terms[0].col; j++) {
				if (j != 0)
					System.out.print(" ");
				if (index > terms[0].value)
					System.out.print("0");
				else if (terms[index].row == i && terms[index].col == j) 
					System.out.print(terms[index++].value);
				else
					System.out.print("0");
			}
		}
		System.out.println("");
	}
	
	public Term getElement(int index) {
		return terms[index];
	}
	
	public SparseMatrix clone() {
		SparseMatrix result = new SparseMatrix(this.terms[0].row, this.terms[0].col);
		for (int i = 1; i <= this.terms[0].value; i++)
			result.add(this.terms[i].row, this.terms[i].col, this.terms[i].value);
		
		return result;
	}
	
	public void transpose() {
		SparseMatrix t = new SparseMatrix(this.terms[0].col, this.terms[0].row);
		for (int i = 0; i <= t.terms[0].row; i++) {
			for (int j = 1; j <= t.terms[0].value; j++) {
				if (this.terms[j].col == i)
					t.add(this.terms[j].col, this.terms[j].row, this.terms[j].value);
			}
		}
		t.print();
		this.terms = t.terms;
	}
	
	public void fastTranspose() {
		
	}
	
	public static SparseMatrix add(SparseMatrix matrix1, SparseMatrix matrix2) {
		int col1 = matrix1.terms[0].col, row1 = matrix1.terms[0].row,
			col2 = matrix2.terms[0].col, row2 = matrix2.terms[0].row;
		//Check if column of matrix1 is equal to row of matrix2.
		if (col1 != col2 || row1 != row2) {
			System.out.println("These two matrixs can not be added!");
			return null;
		}
		SparseMatrix result = new SparseMatrix(row1, col1);
		matrix1.addTail();
		matrix2.addTail();
		//Index of matrix1 and matrix2
		int iM1 = 1, iM2 = 1;
		while (true) {
			if (matrix1.terms[iM1].row == matrix2.terms[iM2].row ) {
				if (matrix1.terms[iM1].col == matrix2.terms[iM2].col) {
					result.add(matrix1.terms[iM1].row, matrix1.terms[iM1].col, (matrix1.terms[iM1].value + matrix2.terms[iM2].value));
					iM1++;
					iM2++;
				}
				else if (matrix1.terms[iM1].col < matrix2.terms[iM2].col) {
					result.add(matrix1.terms[iM1].row, matrix1.terms[iM1].col, matrix1.terms[iM1].value);
					iM1++;
				}
				else if (matrix1.terms[iM1].col > matrix2.terms[iM2].col) {
					result.add(matrix2.terms[iM2].row, matrix2.terms[iM2].col, matrix2.terms[iM2].value);
					iM2++;
				}
			}
			else if (matrix1.terms[iM1].row < matrix2.terms[iM2].row) {
				result.add(matrix1.terms[iM1].row, matrix1.terms[iM1].col, matrix1.terms[iM1].value);
				iM1++;
			}
			else if (matrix1.terms[iM1].row > matrix2.terms[iM2].row) {
				result.add(matrix2.terms[iM2].row, matrix2.terms[iM2].col, matrix2.terms[iM2].value);
				iM2++;
			}
			if (iM1 == matrix1.terms[0].value + 1 && iM2 == matrix2.terms[0].value + 1)
				break;
		}
		matrix1.delTail();
		matrix2.delTail();
		return result;
	}
	
	public static SparseMatrix mult(SparseMatrix matrix1, SparseMatrix matrix2) {
		int column = matrix1.terms[0].col, row = matrix2.terms[0].row;
		//Check if column of matrix1 is equal to row of matrix2.
		if (column != row) {
			System.out.println("These two matrixs can not be multiplied!");
			return null;
		}
		//Row of result matrix would be row of matrix 1 and column would be column of matrix2.
		
		SparseMatrix result = new SparseMatrix(1,1);
		return result;
	}
	
	private void resize() {
		terms = Arrays.copyOf(terms, (terms[0].value + 1) * 2);
	}

}
