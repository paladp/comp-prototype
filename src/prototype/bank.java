package prototype;

import java.util.Hashtable;

public class bank {
	private Hashtable<Object, Integer> accounts  = new Hashtable<Object, Integer>();
	
	public bank() {	
	}
	
	public void createAccount (Object accountHolder, int initialAmount) {
		accounts.put(accountHolder, initialAmount);
	}
	
	public void parseTransaction(transaction transaction_arg) {
		if (accounts.containsKey(transaction_arg.getBuyer()) == true) {
			accounts.put(transaction_arg.getBuyer(), accounts.get(transaction_arg.getBuyer()) - transaction_arg.getAmount());
			System.out.println("Amount on account of " + transaction_arg.getBuyer() + " is: " + accounts.get(transaction_arg.getBuyer()));

		}
	}
	
	public void getAmountOnAccount(Object givenKey) {
		System.out.println("Amount on account of " + givenKey + " is: " + accounts.get(givenKey));
	}
	
	
	
	
}
