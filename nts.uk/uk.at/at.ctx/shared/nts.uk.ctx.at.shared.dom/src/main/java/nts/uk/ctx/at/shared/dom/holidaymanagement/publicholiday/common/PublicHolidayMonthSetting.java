package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common;



import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;

@Getter
@Setter
/**
 * The Class PublicHolidayMonthSetting.
 */
//月間公休日数設定
public class PublicHolidayMonthSetting extends DomainObject{

	/** The public hd management year. */
	// 公休管理年
	private Year publicHdManagementYear;
	
	/** The month. */
	// 月度
	private Integer month;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private MonthlyNumberOfDays inLegalHoliday;
	
	/**
	 * Instantiates a new public holiday month setting.
	 *
	 * @param publicHdManagementYear the public hd management year
	 * @param month the month
	 * @param inLegalHoliday the in legal holiday
	 */
	public PublicHolidayMonthSetting(Year publicHdManagementYear, Integer month, MonthlyNumberOfDays inLegalHoliday){
		this.publicHdManagementYear = publicHdManagementYear;
		this.month = month;
		this.inLegalHoliday = inLegalHoliday;
	}
	
	/**
	 * 年月を作成する
	 * @return
	 */
	public YearMonth createYearMonth(){
		return YearMonth.of(this.getPublicHdManagementYear().v().intValue(), this.getMonth().intValue());
	}
	
	
	/**
	 * 当月残数を取得
	 * @param useData 使用数
	 * @return 残数
	 */
	public LeaveRemainingDayNumber getRemainingDataOfTheMonth(LeaveUsedDayNumber useData){
		return new LeaveRemainingDayNumber(this.inLegalHoliday.v() - useData.v());
	}
	
}
