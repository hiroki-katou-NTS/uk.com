package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

/**
 * 休出振替区分
 * @author shuichu_ishida
 */
public enum HolidayWorkAndTransferAtr {
	/** 休出 */
	HOLIDAY_WORK(0),
	/** 振替 */
	TRANSFER(1);
	
	public int value;
	private HolidayWorkAndTransferAtr(int value){
		this.value = value;
	}
}
