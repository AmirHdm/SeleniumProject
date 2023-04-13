
public class Gurux66 {
	public static void main(String[] args) {
		
		int tab []=FunctExternal.InitXLSX(KeysExternal.TEST_DATA_PATH);
		String[][] Test_Data=FunctExternal.readXLSXFile(KeysExternal.TEST_DATA_PATH, tab[0], tab[1]);	
		
		//  Unit Test of Data reading function  
		
		for(int i = 0; i < Test_Data.length; i++) {
		    for(int j = 0; j < Test_Data[i].length; j++) {
		        System.out.print(Test_Data[i][j]);
		    }
		    System.out.println();
		}
	}

}