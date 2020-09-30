package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

/**
 * 超過フレ区分
 * @author shuichi_ishida
 */
public enum ExcessFlexAtr {
	/** 原則 */
	PRINCIPLE(0),
	/** 便宜上 */
	FOR_CONVENIENCE(1);
	
	public int value;
	private ExcessFlexAtr(int value){
		this.value = value;
	}
}
