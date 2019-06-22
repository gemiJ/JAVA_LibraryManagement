import java.io.*;

public class BookManage extends Book {
	static java.util.Scanner in = null;

	BookManage(){
		super();
	}
	
	void AdminBookManage() {

		showList(true);

		in = new java.util.Scanner(System.in);
		int no;

		System.out.print("���� �߰� => 1 , ���� ����  => 2 , ���ư��� => 3 : ");
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
			FileOutputStream out = new FileOutputStream(book_fn, true);
			boolean cont = true;
			in = new java.util.Scanner(System.in);			
			int len = 0;
			byte[] oneLine = new byte[recordSize];
			// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10
			while (cont) {
				System.out.println("������ȣ : ");
				index = in.nextLine();
				/////////�ε��� ��ġ���� Ȯ���ϰ� �����ؾߵ�	
				len = index.length();
				for (int i = len; i < 5; i++) {
					index = index + " ";
				}				
				fileRead("conindex");
				//�ش絵���� �����ϴ�. 
				if(success==true) {
					System.out.println("�̹� �����ϴ� ������ȣ �Դϴ�.");
					return;
				}
				
				System.out.println("�������� : ");
				name = in.nextLine();
				for (int i = 0; i < recordSize; i++) {
					oneLine[i] = 0;
				}
				len = index.length();
				for (int i = len; i < 5; i++) {
					index = index + " ";
				}
				len = name.length();
				for (int i = len; i < 20; i++) {
					name = name + " ";
				}
				for (int i = 0; i < 26; i++) { // ����,���� ĭ ������ֱ�
					name = name + " ";
				}

				index = index + name;
				oneLine = index.getBytes();

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
		String temp="";
		String temp2="";
		System.out.print("���� �� ");
		Confirm(false);
		//�ش絵���� �����ϴ�. 
		if(success==false) {
			System.out.println("�ش� ������ �����ϴ�.");
			return;
		}
		//���������� Ȯ�� 		
		for (int i=0; i<16; i++) { 
			temp+=" ";
		}
		for (int i=0; i<10; i++) { 
			temp2+=" ";
		}
		if((borrowinfo).compareTo(temp)==0&&(reserveinfo).compareTo(temp2)==0) {
			//������ �ƴϸ�
		System.out.println("���� �����Ͻðڽ��ϱ�?(y/n) : ");
		ans = in.nextLine();
		if (ans.compareTo("y") == 0) {
			try {
				// book ���� ����
				BufferedReader fin = null;
				fin = new BufferedReader(new FileReader(book_fn));

				String line = "";
				String allLine;
				String tempLine = "";
				byte[] allbyte;

				// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

				while ((allLine = fin.readLine()) != null) {
					for (int i = 0; i < allLine.length(); i +=recordSize) {
						line = allLine.substring(i, i + recordSize);
						if (index.compareTo(line.substring(0, 5)) != 0) {
							tempLine += line;
						}
					}

				}
				fin.close();

				FileOutputStream out = new FileOutputStream(book_fn);
				allbyte = tempLine.getBytes();
				out.write(allbyte);
				out.close();

			} catch (IOException e) {
				System.out.println("File Open Error!!!");
			}
			
		}
		}
		else {
			System.out.println("���� ���̰ų� ���� ���� �����Դϴ�.");
		}
	}

}
