package track;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Main
{
	public static final String privateKey = "435FS4wfg23#@$@#";
	public static String decodeBase(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		// helpers.d.b - Decode Base64 and get paramString1 
		//System.out.println("Step 1: helpers.d");
		byte[] arrayOfByte = Base64.decode(paramString1, 2);
		byte[] paramString1Array = Arrays.copyOfRange(arrayOfByte, 0, 16);
		arrayOfByte = Arrays.copyOfRange(arrayOfByte, 16, arrayOfByte.length);
		Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString2.getBytes("utf-8"), "AES");
		IvParameterSpec iv = new IvParameterSpec(paramString1Array);
		localCipher.init(2, localSecretKeySpec, iv);
		paramString1 = new String(localCipher.doFinal(arrayOfByte), "utf-8");
		return paramString1;
	}
	
	public static String getUsernames(String paramString)
	{
		// helpers.a.d - Add p to the start of the password
		//System.out.println("Step 2: helpers.a.d");
		String[] decodedSplit = paramString.split("\n");
		String accountSum = "";
		int j = decodedSplit.length;
		for (int i = 0; i < j; i++)
		{
			String str = decodedSplit[i];
			accountSum = accountSum + "p" + str + "\n";
		}
		return accountSum;
	}
	
	public static String getPasswords(String accountSum)
	{
		// account.b - Replace pT with pT+
		//System.out.println("Step 3: account.b");
		accountSum = accountSum.replaceAll("pT", "pT+");
		return accountSum;
	}
	
	public static void main (String args[]) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		if (args.length==0 || args.length>1)
		{
			System.out.println("[ptdecode] Usage: ptdecode <file>");
			System.exit(1);
		}
		
		File f = new File(args[0]);
		
		if (f.isDirectory() || (!f.exists()))
		{
			System.out.println("[ptdecode] Invalid path specified");
			System.exit(2);
		}
		
		String accountsEncoded = new String(Files.readAllBytes(Paths.get(args[0])));
		
		String decodedAccounts = decodeBase(accountsEncoded, privateKey);
		String usernames = getUsernames(decodedAccounts);
		String passwords = getPasswords(usernames);
		
		String[] usernameArray = usernames.split("\n");
		String[] passwordArray = passwords.split("\n");
		
		Account[] accounts = new Account[usernameArray.length];
		
		for (int i=0; i<accounts.length; i++)
		{
			accounts[i] = new Account(usernameArray[i], passwordArray[i]);
			System.out.println(accounts[i]);
		}
	}
}
