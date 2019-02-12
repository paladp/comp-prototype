package prototype;

public class balancesheet {
	
	public int assets;
	public int liabilities;
	
	public balancesheet (int assets_arg, int liabilities_arg) {
		this.assets = assets_arg;
		this.liabilities = liabilities_arg;
	}
	
	public int getAssets () { return this.assets;}
	
	public int getLiabilities() {return this.liabilities;}
	
	public void increaseAssets (int amount_arg) { this.assets = this.assets + amount_arg; }
	
	public void decreaseAssets (int amount_arg) {this.assets = this.assets - amount_arg;}

}
