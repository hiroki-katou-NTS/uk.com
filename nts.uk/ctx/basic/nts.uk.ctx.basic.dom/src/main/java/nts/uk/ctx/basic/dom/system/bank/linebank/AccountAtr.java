package nts.uk.ctx.basic.dom.system.bank.linebank;
/**
 * Account attribute
 * @author sonnh1
 *
 */
public enum AccountAtr {
	/**
	 * 0 - ìñç¿
	 */
	NORMAL(0),
	/**
	 * 1- ïÅí 
	 */
	CURRENTLY(1);
	
	public final int value;
	AccountAtr(int value){
		this.value = value;
	}
	
}
