import java.io.*;
public class Book {
	String book_fn = "book.bin";
	static java.util.Scanner in = null;
	final int recordSize = 51;
	String index = "";
	String name, borrowinfo, reserveinfo;
	boolean success = false;
	int lentcount = 0;
	String lentstr="";
	// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

	void showList(boolean ad) {

		
		if (ad == false) {
			System.out.println("< ���� ��� >");
			System.out.println("==================================================");
			System.out.println("�ε���   ����                                             ��������                ��������         ");
			System.out.println("==================================================");
			fileRead("list");
		}
		else if (ad == true) {
			System.out.println("< ���� ��� >");
			System.out.println("=========================================================");
			System.out.println("�ε���   ����                                             ��������                                ��������         ");
			System.out.println("=========================================================");
			fileRead("adminlist");
		}
		System.out.println("");
		int search_no;
		System.out.print("�������� �˻��ϱ� => 1 �˻� ���� => 2 : ");
		in = new java.util.Scanner(System.in);
		search_no = in.nextInt();
		if(search_no==1) {
			Search();
		}
		else 
			return ;
	}

	void Search() {
		int len = 0;
		System.out.print("���� ������ �Է��ϼ��� : ");
		in = new java.util.Scanner(System.in);
		name = in.nextLine();
		
		len = name.length();
		for (int i = len; i < 20; i++) {
			name = name + " ";
		}
		
		fileRead("conname");
		if (success == true) {
			bookinfo();
		}
	}
	
	void Confirm(boolean print) {
		int len = 0;
		System.out.print("���� index�� �Է��ϼ��� : ");
		in = new java.util.Scanner(System.in);
		index = in.nextLine();
		len = index.length();
		for (int i = len; i < 5; i++) {
			index = index + " ";
		}

		fileRead("conindex");

		if (print == true && success == true) {
			bookinfo();
		}

	}

	void bookinfo() {
		System.out.println("���� ���� : " + name);
		System.out.println("������� : " + borrowinfo.substring(8));
		System.out.println("������� : " + reserveinfo);
	}

	void fileRead(String option) {
		try {
			BufferedReader fin = null;
			fin = new BufferedReader(new FileReader(book_fn));
			String line = "";
			String allLine;
			// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

			while ((allLine = fin.readLine()) != null) {
				for (int i = 0; i < allLine.length(); i += recordSize) {
					line = allLine.substring(i, i + recordSize);
					
					switch (option) {

					case "list":
						System.out.println(line.substring(0, 5) + " " + line.substring(5, 25) + " "
								+ line.substring(33, 41) + "       " + line.substring(41, 51));
						break;

					case "adminlist":
						System.out.println(line.substring(0, 5) + " " + line.substring(5, 25) + " "
								+ line.substring(25, 41) + "       " + line.substring(41, 51));
						break;
						// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10
					case "conindex":						
						if (index.compareTo(line.substring(0, 5)) == 0) {
							index = line.substring(0, 5);
							name = line.substring(5, 25);
							borrowinfo = line.substring(25, 41);	
							reserveinfo = line.substring(41, 51);
							success = true;
							break;
						}
						break;
						
					case "conname":
						if (name.compareTo(line.substring(5, 25)) == 0) {
							index = line.substring(0, 5);
							name = line.substring(5, 25);
							borrowinfo = line.substring(25, 41);
							reserveinfo = line.substring(41, 51);
							success = true;
							break;
						}
						break;
					}

					// Ư�� id ã��
					if (option.substring(0, 4).compareTo("lent") == 0) {
						if (option.substring(4, 12).compareTo(line.substring(25, 33)) == 0) {
							lentcount++;
						}
					}
					if(option.length()>=8) { //�������� å ��� ���
						if (option.substring(0, 8).compareTo("lentbook") == 0) {
							if (option.substring(8, 16).compareTo(line.substring(25, 33)) == 0) {
								System.out.println(
										line.substring(0, 5) + " " + line.substring(5, 25) + " " + line.substring(33, 41));
								lentstr+=line.substring(0, 5);
							}
						}
					}					
				}
			}

			fin.close();

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
