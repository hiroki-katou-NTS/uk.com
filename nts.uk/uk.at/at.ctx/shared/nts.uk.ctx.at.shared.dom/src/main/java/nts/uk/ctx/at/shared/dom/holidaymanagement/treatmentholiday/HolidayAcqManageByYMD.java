package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
/**
 * 年月日起算の休日取得管理
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.休日の扱い.年月日起算の休日取得管理
 * @author tutk
 *
 */
@Value
public class HolidayAcqManageByYMD implements FourWeekHolidayAcqMana, DomainValue {
	
	/**
	 * 起算日
	 */
	private GeneralDate startingDate;
	
	/**
	 * 4週間の休日日数
	 */
	private FourWeekDays  fourWeekHoliday;

	/**
	 * [2] 管理期間を取得する
	 */
	@Override
	public HolidayAcqManaPeriod getManagementPeriod(Require require, GeneralDate baseDate) {
		int cycleDays = 7*4;
		int numberOfCycles = 0;; 
		GeneralDate startDate;
		GeneralDate endDate;
		if(baseDate.before(startingDate)) {
			numberOfCycles = ( new DatePeriod(baseDate, startingDate).datesBetween().size() -2 ) /cycleDays; 
			endDate = this.startingDate.addDays( ( numberOfCycles * -cycleDays) - 1 );
			startDate = endDate.addDays( 1 - cycleDays );
			
		}else {
			numberOfCycles = ( new DatePeriod(startingDate, baseDate).datesBetween().size() - 1) /cycleDays; 
			startDate = this.startingDate.addDays( numberOfCycles * cycleDays );
			endDate = startDate.addDays( cycleDays - 1 );
		}	
		
		
		return new HolidayAcqManaPeriod( new DatePeriod( startDate, endDate ), this.fourWeekHoliday );
		
	}

	/**
	 * [3] 起算日の種類を取得する
	 */
	@Override
	public StartDateClassification getStartDateType() {
		return StartDateClassification.SPECIFY_YMD;
	}
	
}
