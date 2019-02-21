package prototype;

public class account {
	private Object accountHolder;
	private int amount;
	
	public account (Object givenHolder, int givenAmount) {
		this.accountHolder = givenHolder;
		this.amount = givenAmount;

	}
	
	public Object getHolder ( ) {
		return this.accountHolder;
	}
	
	public int getAmount () { 
		return this.amount;
	}
	
	public void addAmount (int amountToAdd) {
		this.amount = this.amount + amountToAdd;
	}
	
	public void subtractAmount (int amountToSubtract) {
		this.amount = this.amount - amountToSubtract;
	}
	
}
