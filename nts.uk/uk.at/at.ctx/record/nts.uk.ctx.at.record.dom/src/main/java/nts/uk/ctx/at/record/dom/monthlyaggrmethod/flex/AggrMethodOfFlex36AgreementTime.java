package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

/**
 * フレックス36協定時間の集計方法
 * @author shuichu_ishida
 */
public enum AggrMethodOfFlex36AgreementTime {
	/** 所定労働時間から集計 */
	FROM_PREDETERMINE_WORKING_TIME(0),
	/** 法定労働時間から集計 */
	FROM_STATUTORY_WORKING_TIME(1);
	
	public int value;
	private AggrMethodOfFlex36AgreementTime(int value){
		this.value = value;
	}
}
