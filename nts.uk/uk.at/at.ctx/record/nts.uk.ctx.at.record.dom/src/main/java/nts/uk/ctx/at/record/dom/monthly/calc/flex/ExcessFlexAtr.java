package nts.uk.ctx.at.record.dom.monthly.calc.flex;

/**
 * 超過フレ区分
 * @author shuichi_ishida
 */
public enum ExcessFlexAtr {
	/** 原則 */
	Principle(0),
	/** 便宜上 */
	ForConvenience(1);

	/**
	 * 原則か判定する
	 * @return true：原則、false：原則でない
	 */
	public boolean isPrinciple(){
		return this.equals(Principle);
	}
	
	/**
	 * 便宜上か判定する
	 * @return true：便宜上、false：便宜上でない
	 */
	public boolean isForConvenience(){
		return this.equals(ForConvenience);
	}
	
	public int value;
	private ExcessFlexAtr(int value){
		this.value = value;
	}
}
