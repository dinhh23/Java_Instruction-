import java.util.Scanner;

public class ScanMeApp {

	public static void main(String[] args) {
		// Propmt user for 3 numbers on one line then get them *p. 63
		System.out.print("Enter 3 ints separated by a space: ");
		Scanner sc = new Scanner(System.in);
		int n1 = sc.nextInt();
		int n2 = sc.nextInt();
		int n3 = sc.nextInt();
		
		System.out.println("Sum or all #s: "+(n1+n2+n3));
		
	}

}
