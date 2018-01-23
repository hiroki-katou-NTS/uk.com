package nts.uk.ctx.at.record.dom.monthly.calc.flex;

/**
 * 超過フレ区分
 * @author shuichi_ishida
 */
public enum ExcessFlexAtr {
	/** 原則 */
	PRINCIPLE(0),
	/** 便宜上 */
	FOR_CONVENIENCE(1);

	/**
	 * 原則か判定する
	 * @return true：原則、false：原則でない
	 */
	public boolean isPrinciple(){
		return this.equals(PRINCIPLE);
	}
	
	/**
	 * 便宜上か判定する
	 * @return true：便宜上、false：便宜上でない
	 */
	public boolean isForConvenience(){
		return this.equals(FOR_CONVENIENCE);
	}
	
	public int value;
	private ExcessFlexAtr(int value){
		this.value = value;
	}
}
