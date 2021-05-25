package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata;



import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;

/**
 * 
 * @author hayata_maekawa
 *
 *	公休繰越データ
 */
public class PublicHolidayCarryForwardData {

	/*
	 * 社員ID
	 */
	public String employeeId;
	
	/*
	 * 対象月
	 */
	public YearMonth yearMonth;
	
	/*
	 * 期限日
	 */
	public GeneralDate ymd;
	
	/*
	 * 繰越数
	 */
	public LeaveRemainingDayNumber numberCarriedForward;
	
	/*
	 * 登録種別
	 */
	public GrantRemainRegisterType grantRemainRegisterType;
	
	public PublicHolidayCarryForwardData(String employeeId,YearMonth yearMonth,
			GeneralDate ymd, LeaveRemainingDayNumber numberCarriedForward, GrantRemainRegisterType grantRemainRegisterType){
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.ymd = ymd;
		this.numberCarriedForward = numberCarriedForward;
		this.grantRemainRegisterType = grantRemainRegisterType;
	}
}
