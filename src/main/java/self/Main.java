package self;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Controller c=new Controller();
		String[] s= new String[1];
		c.setUrls(s);
		s[0]="http://www.google.com";
	//	Thread t=new Thread(c, "controlerThread");	
new Controller().main(s);
//t.start();
	}

}
