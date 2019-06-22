import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {

	static java.util.Scanner in = null;
	String user_fn = "userinfo.bin";
	String id = " ";
	String name, status, phone , reservebook;
	String datestr;
	final int recordSize = 49;
	byte[] oneLine = new byte[recordSize];
	int returnday;
	int borrownum;
	// id=8, name=10, status=10, phone=11, reservebook=10(�ε���);
	boolean success = false;
	
	//����� ����
	void UserInfo() {
		Confirm();
		if (success == true) {
			System.out.println("ID : " + id);
			System.out.println("�̸� : " + name);
			System.out.println("���� : " + status);
			System.out.println("��ȭ��ȣ : " + phone);			
			System.out.println("���� ���� : " + reservebook);
			System.out.println("���� ����: ");
			Book book = new Book();
			String lentbook = "lentbook" + id;
			System.out.println("================================================");
			System.out.println(
					"�ε���   ����                                             ����ݳ���                               ");
			System.out.println("================================================");
			book.fileRead(lentbook);
			System.out.println("");
		}
	}

	//����� Ȯ��
	void Confirm() {
		int len = 0;
		System.out.print("ID�� �Է��ϼ��� : ");
		in = new java.util.Scanner(System.in);
		id = in.nextLine();
		// ���� ũ�� �����ֱ�
		len = id.length();

		fileRead(false);

		if (success == false) {
			System.out.println("��ϵ��� ���� ID�Դϴ�.");
		}

	}

	//���� �б�
	void fileRead(boolean print) {
		try {
			BufferedReader fin = null;
			int len = 0;
			fin = new BufferedReader(new FileReader(user_fn));
			String line = "";
			String allLine;
			// id=8, name=10, status=10, phone=11, reservebook=10(�ε���);
			while ((allLine = fin.readLine()) != null) {
				// ���� ��
				for (int i = 0; i < allLine.length(); i += recordSize) {
					// System.arraycopy(allLine, i, line, 0, recordSize);
					line = allLine.substring(i, i + recordSize);
					if (print == true) {
						System.out.println(line.substring(0, 8) + " " + line.substring(8, 18) +
								" " + line.substring(18, 28) + " " + line.substring(28, 39)
								+ "      " + line.substring(39, 49));
					} else if (id.compareTo(line.substring(0, 8)) == 0) {
						id = line.substring(0, 8);
						name = line.substring(8, 18);
						status = line.substring(18, 28);
						phone = line.substring(28, 39);					
						reservebook = line.substring(39, 49);
						success = true;
						break;
					}
				}
			}

			fin.close();

		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	void UserClassify() {

		Member mem = null; 
		
		String s = status.trim();
		
		switch(s)
		{
		case "�кλ�":
			mem= new Undergraduate();	
			break;
		case "���п���":
			mem= new Postgraduate();
			break;
		case "����":
			mem = new Professor();
			break;
		case "������":
			mem = new Staff();
			break;
		}
		returnday = mem.Retrundays();
		borrownum = mem.Borrownum();
		
		//System.out.println("�ݳ���:"+mem.Retrundays()+"�� ��");
		//System.out.println("�뿩���� ���� ��:"+mem.Borrownum());

	}

	void UserRental() {
		
		String temp = "";

		//�̿��� Ȯ��
		Confirm();

		if (success == true) {
			
			//�̿��� �з�
			UserClassify();
			
			//��� �������ִ��� Ȯ���ϰ� �������� �ȵȴٰ� �ϱ�. 
			Book book = new Book();
			
			String conlent = "lent"+id;
			book.fileRead(conlent);
			
			if(book.lentcount>=borrownum)
			{
				System.out.println("������ �Ұ����մϴ�.");
				System.out.println("���� �뿩�� å �� ��("+book.lentcount+"), �ִ� �뿩 ���� �Ǽ�("+borrownum+")");
				return ;
			}
			
			//å �뿩			
			book.Confirm(true);
			if (book.success == false) {
				System.out.println("��ϵ��� ���� å�Դϴ�.");
				return ;
			}
			
			for (int i = 0; i < 10; i++) {
				temp += " ";
			}
			
			//å�� ���������� ���ְ� ������ �������� �ε����� �ٸ��� ���� �Ұ���. 
			if ((book.reserveinfo.compareTo(temp) != 0 && book.index.trim().compareTo(reservebook.trim()) != 0)) {
				System.out.println("�������� �����Դϴ�.");
				return ;
			}
			temp="";
			for (int i = 0; i < 16; i++) {
				temp += " ";
			}
			if ((book.borrowinfo).compareTo(temp) == 0) {
				System.out.println("�����Ͻðڽ��ϱ�?(y/n) : ");
				String ans;
				ans = in.nextLine();
				if (ans.compareTo("y") == 0) {
					try {
						int len = 0;
						//�ݳ���¥ �ֱ�
						Date date = new Date();
						SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);

						cal.add(Calendar.DATE,returnday);
						datestr = dataFormat.format(cal.getTime());
							book.borrowinfo = id + datestr;
	
							len = book.borrowinfo.length();
							for (int i = len; i < 16; i++) {
								book.borrowinfo = book.borrowinfo + " ";
							}

							//�����ѻ���� �����Ѵٸ� �������� ���ֱ� 
							if(book.index.trim().compareTo(reservebook.trim()) == 0){
								
								temp="";						
								for (int i = 0; i < 10; i++) {
									temp += " ";
								}
								
								//å�������� �ʱ�ȭ
								book.reserveinfo=temp;
								
								//���� �������� �ʱ�ȭ
								reservebook = temp;
								
								//���� ���� ���� 
								String s = id + name + status + phone + reservebook;
								
								BufferedReader fin = null;
								fin = new BufferedReader(new FileReader(user_fn));
								
								//������ �ʱ�ȭ
								String line = "";
								String allLine = "";
								String tempLine = "";
								byte[] allbyte;
								
								// id=8, name=10, status=10, phone=11, reservebook=10(�ε���);
								while ((allLine = fin.readLine()) != null) {
									for (int i = 0; i < allLine.length(); i += recordSize) {
										line = allLine.substring(i, i + recordSize);
										if (id.compareTo(line.substring(0, 8)) == 0) {
											tempLine += s;
										} else {
											tempLine += line;
										}
									}

								}
								fin.close();
								FileOutputStream out = null;
								out = new FileOutputStream(user_fn);
								allbyte = tempLine.getBytes();
								out.write(allbyte);
								out.close();					
								
								
							}
							
						String s = book.index + book.name + book.borrowinfo + book.reserveinfo;
						//System.out.println(s);

						
						
						// book ���� ����
						BufferedReader fin = null;
						fin = new BufferedReader(new FileReader(book.book_fn));

						String line = "";
						String allLine;
						String tempLine = "";
						byte[] allbyte=null;

						// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

						while ((allLine = fin.readLine()) != null) {
							for (int i = 0; i < allLine.length(); i += book.recordSize) {
								line = allLine.substring(i, i + book.recordSize);
								if (book.index.compareTo(line.substring(0, 5)) == 0) {
									tempLine += s;
								} else {
									tempLine += line;
								}
							}

						}
						fin.close();

						FileOutputStream out = new FileOutputStream(book.book_fn);
						allbyte = tempLine.getBytes();
						out.write(allbyte);
						out.close();

					} catch (IOException e) {
						System.out.println("File Open Error!!!");
					}

					System.out.println("���� �Ϸ�! �ݳ��� " + datestr + "���� �Դϴ�.");
				} else {
					System.out.println("��ҵǾ����ϴ�.");
				}
			} else {
				System.out.println("�̹� ���� ���� �����Դϴ�.");
			}
		}
	}
	
	void UserTurnIn() {
		int len;
		in = new java.util.Scanner(System.in);
		boolean cont = true;
		boolean lentsuccess=false;
		// �̿��� Ȯ��
		Confirm();

		if (success == true) {
			// ���� å ��� ����
			Book book = new Book();
			String lentbook = "lentbook" + id;
			System.out.println("================================================");
			System.out.println(
					"�ε���   ����                                             ����ݳ���                               ");
			System.out.println("================================================");
			book.fileRead(lentbook);
			
			while (cont) {
				// �ݳ��� å �ε��� �Է�				
				System.out.print("�ݳ��Ͻ� ���� index�� �Է��ϼ��� : ");
				in = new java.util.Scanner(System.in);
				book.index = in.nextLine();
				len = book.index.length();
				for (int i = len; i < 5; i++) {
					book.index = book.index + " ";
				}
				
				for(int i=0;i<book.lentstr.length();i+=5) {
					//System.out.println(book.lentstr.substring(i, i + 5));
					if(book.index.compareTo(book.lentstr.substring(i, i + 5))==0)
						lentsuccess=true;					
				}
				book.fileRead("conindex");
				
				// book ���Ͽ��� �� å�� ����
				if (lentsuccess == true) {
					
					try {					
						String temp="";						
							for (int i = 0; i < 16; i++) {
								temp += " ";
							}
							
						book.borrowinfo=temp;

						String s = book.index + book.name + book.borrowinfo + book.reserveinfo;
						//System.out.println(s);

						// book ���� ����
						BufferedReader fin = null;
						fin = new BufferedReader(new FileReader(book.book_fn));

						String line = "";
						String allLine;
						String tempLine = "";
						byte[] allbyte;

						// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

						while ((allLine = fin.readLine()) != null) {
							for (int i = 0; i < allLine.length(); i += book.recordSize) {
								line = allLine.substring(i, i + book.recordSize);
								if (book.index.compareTo(line.substring(0, 5)) == 0) {
									tempLine += s;
								} else {
									tempLine += line;
								}
							}

						}
						fin.close();

						FileOutputStream out = new FileOutputStream(book.book_fn);
						allbyte = tempLine.getBytes();
						out.write(allbyte);
						out.close();

					} catch (IOException e) {
						System.out.println("File Open Error!!!");
					}
					
					System.out.println(book.name+" �ݳ� �Ϸ�");
					book.lentstr="";
			
					
				} else {
					System.out.println("�ش� å�� �����ϴ�.");
				}
				System.out.print("Continue?(y/n) ");
				String ans;
				ans = in.nextLine();
				if (ans.compareTo("y") != 0)
					cont = false;

			}
		}

	}

	void UserReserve() {

		//�̿��� Ȯ��
		Confirm();
		
		//�̹� ���������� Ȯ�� 
		String temp ="";
		for (int i = 0; i < 10; i++) {
			temp += " ";
		}
	
		if ((reservebook).compareTo(temp) == 0) {
		
		//������ å �ε��� �Է�		
		Book book = new Book();
		System.out.print("�����Ͻ� ");
		book.Confirm(true);

		if (book.success == false) {
			System.out.println("��ϵ��� ���� å�Դϴ�.");
			return ;
		}
		
		temp ="";
		//�̹� ����� å�Դϴ�. 
		for (int i = 0; i < 10; i++) {
			temp += " ";
		}
		
		if (book.reserveinfo.compareTo(temp) != 0) {
			System.out.println("�̹� ����� å�Դϴ�.");
			return ;
		}
		
		//�������� y�� ����
		
			System.out.println("�����Ͻðڽ��ϱ�?(y/n) : ");
			String ans;
			ans = in.nextLine();
			if (ans.compareTo("y") == 0) {
				try {
					int len = 0;
						
						book.reserveinfo = "y";					
						len = book.reserveinfo.length();
						
						for (int i = len; i < 10; i++) { //���� ũ�� �����ֱ�
							book.reserveinfo = book.reserveinfo + " ";
						}
				

					String s = book.index + book.name + book.borrowinfo + book.reserveinfo;
					//System.out.println(s);

					// book ���� ����
					BufferedReader fin = null;
					fin = new BufferedReader(new FileReader(book.book_fn));

					String line = "";
					String allLine;
					String tempLine = "";
					byte[] allbyte;

					// index=5, bookname=20, ����(������, �ݳ���)=16, ����=10

					while ((allLine = fin.readLine()) != null) {
						for (int i = 0; i < allLine.length(); i += book.recordSize) {
							line = allLine.substring(i, i + book.recordSize);
							if (book.index.compareTo(line.substring(0, 5)) == 0) {
								tempLine += s;
							} else {
								tempLine += line;
							}
						}

					}
					fin.close();

					FileOutputStream out = new FileOutputStream(book.book_fn);
					allbyte = tempLine.getBytes();
					out.write(allbyte);
					out.close();
	
					//�� �������� ����κп� å�ε����Է�
				
					len = 0;			
					
					reservebook = book.index;
					len = reservebook.length();
					
					for (int i = len; i < 10; i++) { 
						reservebook = reservebook + " ";
					}
				
					s = id + name + status + phone + reservebook;
					fin = new BufferedReader(new FileReader(user_fn));
					
					//������ �ʱ�ȭ
					line = "";
					allLine = "";
					tempLine = "";
					allbyte = null;
					
					// id=8, name=10, status=10, phone=11, reservebook=10(�ε���);
					while ((allLine = fin.readLine()) != null) {
						for (int i = 0; i < allLine.length(); i += recordSize) {
							line = allLine.substring(i, i + recordSize);
							if (id.compareTo(line.substring(0, 8)) == 0) {
								tempLine += s;
							} else {
								tempLine += line;
							}
						}

					}
					fin.close();

					out = new FileOutputStream(user_fn);
					allbyte = tempLine.getBytes();
					out.write(allbyte);
					out.close();					
					
					System.out.println("����Ϸ�"); 				
					
				} catch (IOException e) {
					System.out.println("File Open Error!!!");
				}
				
			} else {
				System.out.println("��ҵǾ����ϴ�.");
			}
		}
		else {
			System.out.println("�̹� �������� ������ �ֽ��ϴ�.");
		}

	}

}
