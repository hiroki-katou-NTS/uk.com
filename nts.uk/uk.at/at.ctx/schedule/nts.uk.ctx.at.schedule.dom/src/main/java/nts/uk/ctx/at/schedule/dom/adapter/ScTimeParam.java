package nts.uk.ctx.at.schedule.dom.adapter;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Value
@Builder
@AllArgsConstructor
public class ScTimeParam {
	//社員ID
	String employeeId;
	
	//年月日
	GeneralDate targetDate;
	
	//勤務種類コード
	WorkTypeCode workTypeCode;
	
	//就業時間帯コード
	WorkTimeCode workTimeCode;
	
	//開始時刻1～2 sorted ASC
	List<Integer> startClock;
	
	//終了時刻1～2 sorted ASC
	List<Integer> endClock;
	
	//休憩開始時刻1~10 sorted ASC
	List<Integer> breakStartTime;
	
	//休憩終了時刻1~10 sorted ASC
	List<Integer> breakEndTime;
	
	//育児介護開始時刻 1~2 sorted ASC
	List<Integer> childCareStartTime;
	
	//育児介護終了時刻 1~2 sorted ASC
	List<Integer> childCareEndTime;
	
	public static ScTimeParam createFrom(BasicSchedule basicSchedule) {
		
		List<Integer> startClock = new ArrayList<>();
		List<Integer> endClock = new ArrayList<>();
		List<Integer> breakStartTime = new ArrayList<>();
		List<Integer> breakEndTime = new ArrayList<>();
		List<Integer> childCareStartTime = new ArrayList<>();
		List<Integer> childCareEndTime = new ArrayList<>();

		// add scheTime
		basicSchedule.getWorkScheduleTimeZones().forEach(x -> {
			startClock.add(x.getScheduleStartClock().v());
			endClock.add(x.getScheduleEndClock().v());
		});

		basicSchedule.getWorkScheduleBreaks().forEach(x -> {
			breakStartTime.add(x.getScheduledStartClock().v());
			breakEndTime.add(x.getScheduledEndClock().v());
		});

		basicSchedule.getChildCareSchedules().forEach(x -> {
			childCareStartTime.add(x.getChildCareScheduleStart().v());
			childCareEndTime.add(x.getChildCareScheduleEnd().v());
		});
		
		return new ScTimeParam(
				basicSchedule.getEmployeeId(),
				basicSchedule.getDate(),
				new WorkTypeCode(basicSchedule.getWorkTypeCode()),
				new WorkTimeCode(basicSchedule.getWorkTimeCode()),
				startClock,
				endClock,
				breakStartTime,
				breakEndTime,
				childCareStartTime,
				childCareEndTime);
	}
	
	public ScTimeParam.ForCache forCache() {
		return new ForCache(workTypeCode, workTimeCode, startClock, endClock, breakStartTime, breakEndTime, childCareStartTime, childCareEndTime);
	}
	
	@Value
	@EqualsAndHashCode
	public static class ForCache {
		
		//勤務種類コード
		WorkTypeCode workTypeCode;
		
		//就業時間帯コード
		WorkTimeCode workTimeCode;
		
		//開始時刻1～2 sorted ASC
		List<Integer> startClock;
		
		//終了時刻1～2 sorted ASC
		List<Integer> endClock;
		
		//休憩開始時刻1~10 sorted ASC
		List<Integer> breakStartTime;
		
		//休憩終了時刻1~10 sorted ASC
		List<Integer> breakEndTime;
		
		//育児介護開始時刻 1~2 sorted ASC
		List<Integer> childCareStartTime;
		
		//育児介護終了時刻 1~2 sorted ASC
		List<Integer> childCareEndTime;
		
	}
}
