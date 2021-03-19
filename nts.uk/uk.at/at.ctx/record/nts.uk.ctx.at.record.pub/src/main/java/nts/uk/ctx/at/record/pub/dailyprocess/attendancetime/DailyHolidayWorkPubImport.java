package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
public class DailyHolidayWorkPubImport {

	//勤務種類
	WorkType workType;
	
	//休出枠時間
	List<HolidayWorkFrameTime> afterCalcUpperTimeList;
	
	//就業時間帯毎の代休設定
	Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet;
	
	//会社共通の代休設定
	Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet;

	/** 代休管理するかどうか */
	boolean isManageCmpLeave;
	
	/**
	 * Constructor 
	 */
	private DailyHolidayWorkPubImport(
			WorkType workType,
			List<HolidayWorkFrameTime> afterCalcUpperTimeList,
			Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			boolean isManageCmpLeave) {
		super();
		this.workType = workType;
		this.afterCalcUpperTimeList = afterCalcUpperTimeList;
		this.eachWorkTimeSet = eachWorkTimeSet;
		this.eachCompanyTimeSet = eachCompanyTimeSet;
		this.isManageCmpLeave = isManageCmpLeave;
	}
	
	public static DailyHolidayWorkPubImport create(WorkType workType, List<HolidayWorkFrameTime> afterCalcUpperTimeList,
						 Optional<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
						 Optional<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
						 boolean isManageCmpLeave) {
		return new DailyHolidayWorkPubImport(
				workType, afterCalcUpperTimeList, eachWorkTimeSet, eachCompanyTimeSet, isManageCmpLeave);
	}
}
