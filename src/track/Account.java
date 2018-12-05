package track;

public class Account {

	public String username;
	public String password;
	
	public Account(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return "ptc,"+username+","+password;
	}
}
