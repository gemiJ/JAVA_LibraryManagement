import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {
	static java.util.Scanner in = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		in = new java.util.Scanner(System.in);
		String passwd="";
		String inputpass ="";
		int no;
		System.out.print("����� ��� => 1 , ������ ��� => 2 : ");
		no =in.nextInt();
		if (no == 1) {
			while (UserMenu())
				;
		} else if (no == 2) {
			// ������ �α���			
			try {			
				inputpass=javax.swing.JOptionPane.showInputDialog("������ ��й�ȣ�� �Է��ϼ���");
				BufferedReader fin = null;
				fin = new BufferedReader(new FileReader("passwd.txt"));	
				String tmp="";
				while ((tmp = fin.readLine()) != null) {
					passwd=tmp;
					//System.out.println(passwd);
				}
				fin.close();
				
				if(passwd.compareTo(inputpass) == 0){			
					while (AdminMenu())
						;
				}
				else {
					JOptionPane.showMessageDialog(null, "�����ڸ�� �α��� ����", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					
				}
				
			} catch (FileNotFoundException e) {
				e.getStackTrace();
			} catch (IOException e) {
				e.getStackTrace();
			}
			
		}

		in.close();

	}

	static boolean UserMenu() {
		int menu_no;
		boolean ret = true;

		in = new java.util.Scanner(System.in);

		System.out.println("*******************");
		System.out.println("     1. ����    ��ȸ          ");
		System.out.println("     2. ��          ��          ");
		System.out.println("     3. ��          ��          ");
		System.out.println("     4. ��          ��          ");
		System.out.println("     5. �̿��� ����          ");
		System.out.println("     6. ��          ��          ");
		System.out.println("*******************");
		System.out.print("input menu number : ");
		menu_no = in.nextInt();

		User user = new User();
		Book book = new Book();

		if (menu_no == 6) {
			ret = false;
		} else {
			if (menu_no >= 1 && menu_no <= 5)
				switch (menu_no) {
				case 1:
					book.showList(false);
					break;
				case 2:
					user.UserRental();
					break;
				case 3:
					user.UserTurnIn();
					break;
				case 4:
					user.UserReserve();
					break;
				case 5:
					user.UserInfo();
					break;
				}
			else
				System.out.println("�޴� ��ȣ�� �ٽ� �Է��ϼ���");
			ret = true;
		}

		return ret;
	}

	static boolean AdminMenu() {	
		int menu_no = 0;
		boolean ret = true;
		in = new java.util.Scanner(System.in);

		System.out.println("********************");
		System.out.println("     1. ��           ��          ");
		System.out.println("     2. ��           ��          ");
		System.out.println("     3. ��           ��          ");
		System.out.println("     4. å ��� ����          ");// å �߰� ��� ����
		System.out.println("     5. �̿���  ����          ");
		System.out.println("     6. ��           ��          ");
		System.out.println("********************");
		System.out.print("input menu number : ");

		menu_no = in.nextInt();

		BookManage bm = new BookManage();
		UserManage um = new UserManage();

		if (menu_no == 6) {
			ret = false;
		} else {
			if (menu_no >= 1 && menu_no <= 5)
				switch (menu_no) {
				case 1:
					um.AdminRental();
					break;
				case 2:
					um.AdminTurnIn();
					break;
				case 3:
					um.AdminReserve();
					break;
				case 4:
					bm.AdminBookManage();
					break;
				case 5:
					um.AdminUserManage();
					break;
				}
			else
				System.out.println("�޴���ȣ�� �ٽ� �Է��ϼ���");
			ret = true;
		}

		return ret;

	}

}
