package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 
 * @author nampt
 * 日別実績の乖離時間
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeOfDaily {
	
	/** 乖離時間 */
	private List<DivergenceTime> divergenceTime;
	
	public static DivergenceTimeOfDaily create(
			DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			List<DivergenceTimeRoot> divergenceTimeList,
			CalAttrOfDailyAttd calcAtrOfDaily,
			Optional<TimezoneOfFixedRestTimeSet> breakTimeSheets,
			TotalWorkingTime calcResultOotsuka,
			Optional<WorkTimeSetting> workTimeSetting,
			Optional<WorkType> workType) {
		
		val returnList = DivergenceTimeRoot.calcDivergenceTime(
				forCalcDivergenceDto, divergenceTimeList, calcAtrOfDaily, breakTimeSheets, calcResultOotsuka,
				workTimeSetting, workType);
		//returnする
		return new DivergenceTimeOfDaily(returnList);
	}
}
