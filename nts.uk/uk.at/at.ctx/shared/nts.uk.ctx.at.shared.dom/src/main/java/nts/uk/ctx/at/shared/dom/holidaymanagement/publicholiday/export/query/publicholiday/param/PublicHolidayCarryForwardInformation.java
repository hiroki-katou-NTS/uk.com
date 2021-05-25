package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;

/**
 * 
 * @author hayata_maekawa
 *
 *	公休繰越情報
 */
public class PublicHolidayCarryForwardInformation {

	
	
	/*
	 * 翌月繰越数
	 */
	private LeaveRemainingDayNumber carryForwardNumber;

	/*
	 * 未消化数
	 */
	private LeaveRemainingDayNumber unusedNumber;

	/**
	 * コンストラクタ
	 */
	
	public PublicHolidayCarryForwardInformation(){
		this.carryForwardNumber = new LeaveRemainingDayNumber(0.0);
		this.unusedNumber = new LeaveRemainingDayNumber(0.0);
	}
	
	public PublicHolidayCarryForwardInformation(LeaveRemainingDayNumber carryForwardNumber,
			LeaveRemainingDayNumber unusedNumber){
		this.carryForwardNumber = carryForwardNumber;
		this.unusedNumber = unusedNumber;
	}
	

}


