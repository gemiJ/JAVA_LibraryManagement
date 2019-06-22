import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class UserManage extends User {
	static java.util.Scanner in = null;

	UserManage() {
		super();
	}
	
	void showList() {

		System.out.println("< ����� ��� >");
		System.out.println("===================================================");
		System.out.println("ID        �̸�           ����           ��ȭ��ȣ                    ���� ����             ");
		System.out.println("===================================================");
		fileRead(true);
		System.out.println("");
	}

	void AdminRental() {
		
		while (true) {
			int menu_no;
			in = new java.util.Scanner(System.in);
			System.out.println("******************");
			System.out.println("�̿��� ����Ʈ ���� ==> 1");
			System.out.println("��������Ʈ ����     ==> 2");
			System.out.println("�����ϱ�               ==> 3");
			System.out.println("���ư���               ==> 4");
			System.out.println("******************");
			System.out.print("input menu number :");
			menu_no = in.nextInt();
			
			Book book = new Book();
			
			switch (menu_no) {
			case 1:
				showList();
				break;
			case 2:
				book.showList(true);
				break;
			case 3:
				UserRental();
				break;
			case 4:
				return;
			default:
				System.out.println("�޴� ��ȣ�� �ٽ� �Է��ϼ���");
			}
		}
	}

	void AdminTurnIn() {
		while (true) {
			int menu_no;
			in = new java.util.Scanner(System.in);
			System.out.println("******************");
			System.out.println("�̿��� ����Ʈ ���� ==> 1");
			System.out.println("��������Ʈ ����     ==> 2");
			System.out.println("�ݳ��ϱ�               ==> 3");
			System.out.println("���ư���               ==> 4");
			System.out.println("******************");
			System.out.print("input menu number :");
			menu_no = in.nextInt();
			
			Book book = new Book();
			
			switch (menu_no) {
			case 1:
				showList();
				break;
			case 2:
				book.showList(true);
				break;
			case 3:
				UserTurnIn();
				break;
			case 4:
				return;
			default:
				System.out.println("�޴� ��ȣ�� �ٽ� �Է��ϼ���");
			}
		}
		
	}

	void AdminReserve() {
		while (true) {
			int menu_no;
			in = new java.util.Scanner(System.in);
			System.out.println("******************");
			System.out.println("�̿��� ����Ʈ ���� ==> 1");
			System.out.println("��������Ʈ ����     ==> 2");
			System.out.println("�����ϱ�               ==> 3");
			System.out.println("���ư���               ==> 4");
			System.out.println("******************");
			System.out.print("input menu number :");
			menu_no = in.nextInt();
			
			Book book = new Book();
			
			switch (menu_no) {
			case 1:
				showList();
				break;
			case 2:
				book.showList(true);
				break;
			case 3:
				UserReserve();
				break;
			case 4:
				return;
			default:
				System.out.println("�޴� ��ȣ�� �ٽ� �Է��ϼ���");
			}
		}
		
	}

	void AdminUserManage() {

		showList();

		in = new java.util.Scanner(System.in);
		int no;

		System.out.print("����� �߰� => 1 , ����� ����  => 2 , ���ư��� => 3 : ");
		no = in.nextInt();

		if (no == 1) {
			addItem();
		} else if (no == 2) {
			eraseItem();
		} else if (no == 3) {
			;
		}

	}

	void addItem() {
		try {
			FileOutputStream out = new FileOutputStream(user_fn, true);
			boolean cont = true;
			in = new java.util.Scanner(System.in);

			int no = 0;

			int len = 0;
			// id=8, name=10, status=10, phone=11, reservebook=10(�ε���);
			while (cont) {
				System.out.println("ID : ");
				id = in.nextLine();
				
				len = id.length();
				for (int i = len; i < 8; i++) {
					id = id + " ";
				}				
				
				fileRead(false);
				if (success == true) {
					System.out.println("�̹� �����ϴ� ID�Դϴ�.");
					return ;
				}

				System.out.println("�̸� : ");
				name = in.nextLine();
				System.out.println("�кλ� => 1 ���п��� =>2 ���� =>3 ������ =>4 : "); // �Է½� �ѱ� ������ ����..
				no = Integer.parseInt(in.nextLine());
				if (no == 1) {
					status = "�кλ�";
				} else if (no == 2) {
					status = "���п���";
				} else if (no == 3) {
					status = "����";
				} else if (no == 4) {
					status = "������";
				}
				System.out.println("��ȭ��ȣ : ");
				phone = in.nextLine();

				for (int i = 0; i < recordSize; i++) {
					oneLine[i] = 0;
				}
				len = id.length();
				for (int i = len; i < 8; i++) {
					id = id + " ";
				}
				len = name.length();
				for (int i = len; i < 10; i++) {
					name = name + " ";
				}
				len = status.length();
				for (int i = len; i < 10; i++) {
					status = status + " ";
				}
				len = phone.length();
				for (int i = len; i < 11; i++) {
					phone = phone + " ";
				}
				for (int i = 0; i < 10; i++) { // ���� ĭ ������ֱ�
					phone = phone + " ";
				}

				id = id + name + status + phone;
				oneLine = id.getBytes();

				out.write(oneLine);
				System.out.print("Continue?(y/n) ");
				String ans;
				ans = in.nextLine();
				if (ans.compareTo("y") != 0)
					cont = false;
			}

			out.close();

		} catch (IOException e) {
			System.out.println("File Open Error!!!");
		}

	}

	void eraseItem() {
		in = new java.util.Scanner(System.in);
		String ans;
		String temp = "";
		System.out.print("���� �� ");
		Confirm();
		// �ش絵���� �����ϴ�.
		if (success == false) {
			return;
		}
		// ���������� Ȯ��
		for (int i = 0; i < 16; i++) {
			temp += " ";
		}
		String temp2 = "";
		for (int i = 0; i < 10; i++) {
			temp2 += " ";
		}
		

		//å�����о �� �й� ���� å �ִ��� Ȯ�� �ϰ� �������, ���� ���� �������. 
		boolean delete = false;
		Book book = new Book();
		String conlent = "lent"+id;
		book.fileRead(conlent);
		if(book.lentcount == 0)
		{
			 delete = true;
		}
		
		if (delete == true && (reservebook).compareTo(temp2) == 0) {
			// ������ �ƴϸ�
			System.out.println("���� �����Ͻðڽ��ϱ�?(y/n) : ");
			ans = in.nextLine();
			if (ans.compareTo("y") == 0) {
				try {
					// book ���� ����
					BufferedReader fin = null;
					fin = new BufferedReader(new FileReader(user_fn));

					String line = "";
					String allLine;
					String tempLine = "";
					byte[] allbyte;

					// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

					while ((allLine = fin.readLine()) != null) {
						for (int i = 0; i < allLine.length(); i += recordSize) {
							line = allLine.substring(i, i + recordSize);
							if (id.compareTo(line.substring(0, 8)) != 0) {
								tempLine += line;
							}
						}

					}

					fin.close();

					FileOutputStream out = new FileOutputStream(user_fn);
					allbyte = tempLine.getBytes();
					out.write(allbyte);
					out.close();

				} catch (IOException e) {
					System.out.println("File Open Error!!!");
				}

			}
		} else {
			System.out.println("�������̰ų� �������� ������ �ֽ��ϴ�.");
		}
	}

}
