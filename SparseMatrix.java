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
		Term[] trans = new Term[this.terms[0].value];
		trans[0] = new Term();
		trans[0].col = this.terms[0].row;
		trans[0].row = this.terms[0].col;
		trans[0].value = this.terms[0].value;
		int index = 1;
		for (int i = 0; i <= trans[0].row; i++) {
			for (int j = 1; j <= trans[0].value; j++) {
				if (this.terms[j].col == i) {
					if (index >= trans.length)
						trans = Arrays.copyOf(trans, index * 2);
					trans[index] = new Term();
					trans[index].row = i;
					trans[index].col = this.terms[j].row;
					trans[index].value = this.terms[j].value;
					index++;
				}
			}
		}
		this.terms = trans;
	}
	
	public void fastTranspose() {
		//count: how many value need to be transpose.
		int count = this.terms[0].value;
		Term[] trans = new Term[count + 1];
		
		trans[0] = new Term(this.terms[0].col, this.terms[0].row, count);
		int[] rowTerms = new int[this.terms[0].col], startingPos = new int[this.terms[0].col];
		//If the matrix is not a zero matrix, transpose it.
		if (trans[0].value > 0) {
			//Fill 0 to all elements in rowTerms so that it can be added in next step.
			for (int i = 0; i < rowTerms.length; i++)
				rowTerms[i] = 0;
			for (int i = 1; i < count; i++)
				rowTerms[this.terms[i].col]++;
			startingPos[0] = 1;
			for (int i = 1; i < startingPos.length; i++)
				startingPos[i] = startingPos[i - 1] + rowTerms[i - 1];
			
			for (int i = 1; i <= count; i++) {
				int index = startingPos[terms[i].col]++;
				trans[index] = new Term();
				trans[index].row = terms[i].col;
				trans[index].col = terms[i].row;
				trans[index].value = terms[i].value;
			}
			
			this.terms = trans;
		}
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
	
	public static SparseMatrix mult(SparseMatrix matrixA, SparseMatrix matrixB) {
		int rowA = matrixA.terms[0].row, colA = matrixA.terms[0].col, totalA = matrixA.terms[0].value, 
			rowB = matrixB.terms[0].row, colB = matrixB.terms[0].col, totalB = matrixB.terms[0].value;
		
		//Check if column of matrix1 is equal to row of matrix2.
		if (colA != rowB) {
			System.out.println("These two matrixs can not be multiplied!");
			return null;
		}
		//Row of result matrix would be row of matrixA and column would be column of matrixB.
		//transpose matrix b and create new term[] to store result.
		SparseMatrix newB = matrixB.clone();
		newB.transpose();
		Term[] d = new Term[rowA * colB + 1], a = matrixA.terms, b = newB.terms;
		//Record store numbers in term[] d.
		int totalD = 0, row = 0, col, i = 1, sum;
		
		//Make margin of Term[].
		if (totalA + 1 >= a.length)
			a = Arrays.copyOf(a, a.length + 1);
		a[totalA + 1] = new Term(a[0].row, 0, 0);
		if (totalB + 1 >= b.length)
			b = Arrays.copyOf(b, b.length + 1);
		b[totalB + 1] = new Term(b[0].row,0 ,0);
		
		//Do multiple.
		while (i <= totalA) {
			sum = 0;
			col = b[1].row;
			int j = 1; //index for b[].
			while (j <= totalB) {
				if (a[i].row != row) {
					totalD = storeSum(d, totalD, row, col, sum);
				}
			}
			
		}
		
		
		//Create new sparsematrix to store result and assign d as terms.
		SparseMatrix result = new SparseMatrix(rowA, colB);
		result.terms = d;
		
		return result;
	}
	
	public static SparseMatrix mMult(SparseMatrix aA, SparseMatrix bB) {  //將兩個矩陣內藏的陣列做矩陣相乘運算
	    Term[] a = aA.terms, b = bB.terms;
		int i = 1, column, totalB = b[0].value, totalD = 0;
	    int rowsA = a[0].row, colsA = a[0].col,
	        totalA = a[0].value, colsB = b[0].col;  //totalA是第一個矩陣非零項的個數
	    int rowBegin = 1, row = a[1].row, sum = 0;
	    if (colsA != b[0].row)
	    		return null;
	    SparseMatrix transB = bB.clone();
	    transB.fastTranspose();
	    Term[] newB = transB.terms;
	    Term[] d = new Term[a[0].row * b[0].col+1];
	 // 設定邊界狀況
	    if (totalA + 1 >= a.length)
	    		a = Arrays.copyOf(a, a.length + 1);
	    a[totalA + 1] = new Term();
        a[totalA + 1].row = rowsA;
        if (totalB + 1 >= newB.length) 
        		newB = Arrays.copyOf(newB, newB.length + 1);
        newB[totalB + 1] = new Term();
        newB[totalB + 1].row = colsB;
        newB[totalB + 1].col = 0;
        while (i <= totalA) { // i: index of Term[] a;
        		column = newB[1].row;
            int j = 1; // j: index of Term[] newB;
            sum = 0;
            while (j <= totalB + 1){
                // a的row *= b的column
                if (a[i].row != row){
                		totalD = storeSum(d, totalD, row, column, sum);
                		sum = 0;
                    i = rowBegin;
                		while (newB[j].row == column)
                        j++;
                    column = newB[j].row;
               }
                else if (newB[j].row != column){
                		totalD = storeSum(d, totalD, row, column, sum);
                		sum = 0;
                    i = rowBegin;
                    column = newB[j].row;
                }
                else {
                		if (a[i].col - newB[j].col < 0)   // go to next term in a
                			i++;
                    else if (a[i].col == newB[j].col) //add terms, go to next term in a and b
                    		sum += (a[i++].value * newB[j++].value);
                    else  // advance to next term in b
                    		j++;
                }
            }
            while (a[i].row == row)
            		i++;
            rowBegin = i;
            row = a[i].row;
//                    rowBegin = i;
//                    row = a[i].row;
//                    i++;
                
         } //end of for i <= totalA 
         d[0] = new Term();
         d[0].row = rowsA;
         d[0].col = colsB;
         d[0].value = totalD;
         d = Arrays.copyOf(d, totalD + 1);
         transB.terms = d;
 	    return transB;
        
	}
        
	private static int storeSum(Term[] d, int totalD, int row, int column, int sum)
	{ /* 假如sum != 0, 則sum的值與其所在的列與行會被存在d中totalD+1的位置*/
		if (sum != 0) {
			d[++totalD] = new Term();
			d[totalD].row = row;
	        d[totalD].col = column;
	        d[totalD].value = sum;
	    }
		return totalD;
	}


	
	private void resize() {
		terms = Arrays.copyOf(terms, (terms[0].value + 1) * 2);
	}

}
