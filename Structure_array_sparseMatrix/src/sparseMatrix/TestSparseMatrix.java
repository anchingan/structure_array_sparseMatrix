package sparseMatrix;

public class TestSparseMatrix {

	public static void main(String[] args) {
		SparseMatrix test1 = new SparseMatrix(3,3);
//		SparseMatrix test = new SparseMatrix(1,2);
		int[][] a = {{1, 2, 0}, {6, 1, 7}, {0, 0, 0}};
		test1.create(a);
//		test.create(new int[][] {{3,5}});
//		test1.print();
		SparseMatrix test2 = new SparseMatrix(3,3);
		test2.create(new int[][] {{0, 1, 0}, {0, 0, 5},{1, 0, 8}});
		test1.print();
		System.out.println("");
		test2.print();
		System.out.println("");
		
		SparseMatrix clone = test1.clone();
		clone.print();
		clone.transpose();
		clone.print();
//		if (clone != null)
//			clone.print();
		

	}

}
