package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
public class DailyHolidayWorkPubImport {

	//社員ID
	String employeeId;
	
	//年月日
	GeneralDate ymd;
	
	//勤務種類
	WorkType workType;
	
	//休出枠時間
	List<HolidayWorkFrameTime> afterCalcUpperTimeList;
	
	//就業時間帯毎の代休設定
	Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet;
	
	//会社共通の代休設定
	Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet;
	
	/**
	 * Constructor 
	 */
	private DailyHolidayWorkPubImport(
			String employeeId,
			GeneralDate ymd,
			WorkType workType,
			List<HolidayWorkFrameTime> afterCalcUpperTimeList,
			Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workType = workType;
		this.afterCalcUpperTimeList = afterCalcUpperTimeList;
		this.eachWorkTimeSet = eachWorkTimeSet;
		this.eachCompanyTimeSet = eachCompanyTimeSet;
	}
	
	public static DailyHolidayWorkPubImport create(
			String employeeId,
			GeneralDate ymd,
			WorkType workType,
			List<HolidayWorkFrameTime> afterCalcUpperTimeList,
			Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet) {
		
		return new DailyHolidayWorkPubImport(
				employeeId, ymd, workType, afterCalcUpperTimeList, eachWorkTimeSet, eachCompanyTimeSet);
	}
}
