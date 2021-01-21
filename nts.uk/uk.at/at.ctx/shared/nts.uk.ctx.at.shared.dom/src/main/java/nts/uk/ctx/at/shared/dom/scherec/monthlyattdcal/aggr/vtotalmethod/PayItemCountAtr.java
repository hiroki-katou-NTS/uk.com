package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

/**
 * 項目区分
 * @author shuichu_ishida
 */
public enum PayItemCountAtr {
	/** 給与出勤日数 */
	PAY_ATTENDANCE_DAYS(0),
	/** 給与欠勤日数 */
	PAY_ABSENCE_DAYS(1);
	
	public int value;
	private PayItemCountAtr(int value){
		this.value = value;
	}
}
