package prototype;

public class transaction {
	private static int currentTransactionID = 0;
	private int transactionID;
	private int amount;
	private Object seller;
	private Object buyer;
	
	public transaction (int amount_arg, Object seller_arg, Object buyer_arg) {
		this.amount = amount_arg;
		this.seller = seller_arg;
		this.buyer = buyer_arg;
		this.transactionID = currentTransactionID + 1;
		currentTransactionID++;
	}
	
	public Object getSeller() { return this.seller; }
	
	public Object getBuyer() { return this.buyer; }
	
	public int getAmount() { return this.amount;}
}
