package sparseMatrix;

public class Term {
	int row, col, value;
	
	public Term() {
		this.row = 0;
		this.col = 0;
		this.value = 0;
	}
	
	public Term(int row, int col, int value) {
		this();
		if (row > 0)
			this.row = row;
		if (col > 0)
			this.col = col;
		this.value = value;
	}
	
//	public int getRow() {
//		return this.row;
//	}
//	
//	public int getCol() {
//		return this.col;
//	}
//	
//	public int getValue() {
//		return this.value;
//	}

}
