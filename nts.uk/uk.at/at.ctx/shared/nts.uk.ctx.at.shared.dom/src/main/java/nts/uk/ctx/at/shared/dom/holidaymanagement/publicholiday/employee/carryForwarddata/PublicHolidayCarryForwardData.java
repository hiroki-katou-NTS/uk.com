package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata;



import nts.arc.layer.dom.objecttype.DomainAggregate;
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
public class PublicHolidayCarryForwardData implements DomainAggregate{

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
	
	/**
	 * 削除するか判断する
	 * @param endDay
	 * @return
	 */
	public boolean deleteDecision(GeneralDate endDay){
		return (this.numberCarriedForward.v() == 0.0 || 
				this.ymd.beforeOrEquals(endDay));
	}
	
	/**
	 * //繰り越されてきた取れてない日数と相殺
	 * @param carryForwardNotAcquired 繰越されてきた取れていない日数
	 * @return
	 */
	public double offsetUnacquiredDays(double carryForwardNotAcquired){
		double x = Math.abs(this.numberCarriedForward.v()/carryForwardNotAcquired);
		if (x > 1) {
			return carryForwardNotAcquired;
		}else{
			return this.numberCarriedForward.v();
		}
	}
	
}
