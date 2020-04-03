package nts.uk.ctx.at.request.dom.application;
/**
 * 事前事後区分
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
	POSTERIOR(1),
	
	/**
	 * 2: 選択なし
	 */
	NONE(2);
	
	public int value;
	
	PrePostAtr(int type){
		this.value = type;
	}
	
	
}
