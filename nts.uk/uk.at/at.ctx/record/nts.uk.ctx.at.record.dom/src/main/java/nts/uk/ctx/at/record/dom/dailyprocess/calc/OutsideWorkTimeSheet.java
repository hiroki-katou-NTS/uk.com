package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import lombok.Getter;
import lombok.Value;

/**
 * 就業時間外時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class OutsideWorkTimeSheet {
	private Optional<OverTimeWorkSheet> overTimeWorkSheet;
	
	private Optional<HolidayWorkTimeSheet> holidayWorkTimeSheet;

	public OutsideWorkTimeSheet(Optional<OverTimeWorkSheet> overTimeWorkSheet,Optional<HolidayWorkTimeSheet> holidayWorkTimeSheet) {
			setOverTimeWorkSheet(overTimeWorkSheet);
			setHolidayWorkTimeSheet(holidayWorkTimeSheet);
	}

	/**
	 * 残業時間帯のセッター
	 * @param holidayWorkTimeSheet 残業時間帯
	 */
	private void setHolidayWorkTimeSheet(Optional<HolidayWorkTimeSheet> holidayWorkTimeSheet) {
		if(holidayWorkTimeSheet.isPresent()) {
			this.holidayWorkTimeSheet = holidayWorkTimeSheet;
		}
		else {
			this.holidayWorkTimeSheet = Optional.empty();
		}
	}

	/**
	 * 休出時間帯のセッター
	 * @param overTimeWorkSheet　休日出勤時間帯
	 */
	private void setOverTimeWorkSheet(Optional<OverTimeWorkSheet> overTimeWorkSheet) {
		if(overTimeWorkSheet.isPresent()) {
			this.overTimeWorkSheet = overTimeWorkSheet;
		}
		else {
			this.overTimeWorkSheet = Optional.empty();
		}
	}
	
	/**
	 * 法定外深夜時間の計算
	 */
	public OutsideWorkTimeSheet calcMidNightTimeIncludeExcessWorkTime(Optional<OverTimeWorkSheet> overTimeWorkSheet,Optional<HolidayWorkTimeSheet> holidayWorkSheet) {
		int excessTime = 0;
		Optional<OverTimeWorkSheet> overTimeWork = Optional.empty();
		Optional<HolidayWorkTimeSheet> holidayTimeSheet = Optional.empty();
		if(overTimeWorkSheet.isPresent()) {
			overTimeWork = Optional.of(overTimeWorkSheet.get().reCreateToCalcExcessWork());
			excessTime = overTimeWork.orElse(0);
		}
		if(holidayWorkSheet.isPresent()) {
			holidayWorkSheet = Optional.of(holidayWorkSheet.get().reCreateToCalcExcessWork());
		}
		
		return new OutsideWorkTimeSheet(overTimeWork,holidayTimeSheet);
	}
	
	

}
