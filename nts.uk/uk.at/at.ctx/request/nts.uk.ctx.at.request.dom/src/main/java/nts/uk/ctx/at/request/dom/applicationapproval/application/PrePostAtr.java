package nts.uk.ctx.at.request.dom.applicationapproval.application;
/**
 * 
 * @author tutk
 *
 */
public enum PrePostAtr {

	/**
	 * 0: 事前の受付制限
	 */
	PREDICT(0),
	/**
	 * 1: 事後の受付制限
	 */
	POSTERIOR(1);
	
	public int value;
	
	PrePostAtr(int type){
		this.value = type;
	}
	
	
}
