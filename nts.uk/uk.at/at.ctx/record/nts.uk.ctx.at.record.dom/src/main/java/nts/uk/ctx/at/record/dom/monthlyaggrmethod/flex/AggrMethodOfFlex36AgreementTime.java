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

	/**
	 * 所定労働時間から集計か判定する
	 * @return true：所定労働時間から集計、false：所定労働時間から集計でない
	 */
	public boolean isFromPrescribedWorkingTime(){
		return this.equals(FROM_PREDETERMINE_WORKING_TIME);
	}
	
	/**
	 * 法定労働時間から集計か判定する
	 * @return true：法定労働時間から集計、false：法定労働時間から集計でない
	 */
	public boolean isFromStatutoryWorkingTime(){
		return this.equals(FROM_STATUTORY_WORKING_TIME);
	}
	
	public int value;
	private AggrMethodOfFlex36AgreementTime(int value){
		this.value = value;
	}
}
