package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave;

/**
 * 休出振替区分
 * @author shuichu_ishida
 */
public enum ProcAtrHolidayWorkAndTransfer {
	/** 休出 */
	HOLIDAY_WORK(0),
	/** 振替 */
	TRANSFER(1);
	
	public int value;
	private ProcAtrHolidayWorkAndTransfer(int value){
		this.value = value;
	}
}
