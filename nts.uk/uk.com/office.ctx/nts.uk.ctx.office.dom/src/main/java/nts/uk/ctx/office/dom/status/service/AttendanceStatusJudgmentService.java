package nts.uk.ctx.office.dom.status.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.office.dom.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.ctx.office.dom.dto.WorkTypeDto;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.StatusClassfication;

public class AttendanceStatusJudgmentService {

	public static Optional<ActivityStatus> getActivityStatus(Required required, String sId,
					GeneralDate baseDate,Optional<WorkInfoOfDailyPerformanceDto> workInfo,
					Optional<TimeLeavingOfDailyPerformanceDto> timeLeave, 
					Optional<WorkTypeDto>  workType) {
		Optional<GoOutEmployeeInformation> goOutInfo = required.getGoOutEmployeeInformation(sId, baseDate);	//	外出情報
		Optional<ActivityStatus> status = required.getActivityStatus(sId, baseDate);	//	ステータス
		Optional<Integer> attendanceTime = Optional.empty();;	//	出勤時刻
		Optional<Integer> leaveTime = Optional.empty();	//	退勤時刻
		Optional<Integer> goStraightAtr= Optional.empty();	//	直行区分
		Optional<Integer> workDivision = Optional.empty(); 	//	勤務区分
		ActivityStatus activityStatus = new ActivityStatus();
		if(timeLeave.isPresent()) {
			//	出勤時刻　＝　日別実績の出退勤.出勤時刻
			attendanceTime = Optional.ofNullable(timeLeave.get().getAttendanceTime());
			//	退勤時刻　＝　日別実績の出退勤.退勤時刻
			leaveTime = Optional.ofNullable(timeLeave.get().getLeaveTime());
		}
		//	直行区分　＝　日別実績の勤務情報.isPrensent　？　日別実績の勤務情報.直行区分　：　しない
		goStraightAtr = workInfo.isPresent() ? Optional.of(workInfo.get().getBackStraightAtr()) : Optional.empty();
		List<Integer> notIn = new ArrayList<>();
		notIn.add(0);	//	出勤
		notIn.add(11);	//	休日出勤
		notIn.add(7);	//	振出
		notIn.add(10);	//	連続勤務
		if (workType.get().getDailyWork().getOneDay() != null && 
				!notIn.contains(workType.get().getDailyWork().getOneDay())) {
			workDivision = Optional.of(1); 	//	休み
		} else {
			workDivision = Optional.of(0);	//	出勤
		}
		
		Integer timeNow = GeneralDateTime.now().hours() * 100 + GeneralDateTime.now().minutes();
		
		if (baseDate != GeneralDate.today()) {
			return Optional.empty();
		}

		if (goOutInfo.isPresent() && goOutInfo.get().getGoOutTime().v() <= timeNow 
				&& goOutInfo.get().getComebackTime().v() >= timeNow 
				&& !leaveTime.isPresent() || goOutInfo.isPresent()
				&& goOutInfo.get().getGoOutTime().v() <= timeNow 
				&& goOutInfo.get().getComebackTime().v() >= timeNow 
				&& leaveTime.get() > timeNow) {
			activityStatus.setActivity(StatusClassfication.GO_OUT);
			return Optional.of(activityStatus);
		}
		
		if (!workDivision.isPresent() && !attendanceTime.isPresent() 
				&& status.isPresent() && !leaveTime.isPresent()) {
			activityStatus.setActivity(status.get().getActivity());
			return Optional.of(activityStatus);
		}
		
		if (!workDivision.isPresent() && !attendanceTime.isPresent() 
				&& !status.isPresent() && !leaveTime.isPresent()) {
			activityStatus.setActivity(StatusClassfication.NOT_PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1
				&& !attendanceTime.isPresent() && !leaveTime.isPresent()
				&& status.isPresent()) {
			activityStatus.setActivity(status.get().getActivity());
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1
				&& !attendanceTime.isPresent() && !leaveTime.isPresent()
				&& !status.isPresent()) {
			activityStatus.setActivity(StatusClassfication.HOLIDAY);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1
				&& leaveTime.isPresent() && leaveTime.get() <= timeNow) {
			activityStatus.setActivity(StatusClassfication.GO_HOME);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1 
				&& attendanceTime.isPresent() && attendanceTime.get() <= timeNow) {
			activityStatus.setActivity(StatusClassfication.GO_HOME);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1 
				&& !attendanceTime.isPresent() && leaveTime.isPresent() 
				&& leaveTime.get() <= timeNow) {
			activityStatus.setActivity(StatusClassfication.GO_HOME);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1 
				&& !leaveTime.isPresent()) {
			activityStatus.setActivity(StatusClassfication.HOLIDAY);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1  
				&& status.isPresent()) {
			activityStatus.setActivity(status.get().getActivity());
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 1) {
			activityStatus.setActivity(StatusClassfication.PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& !attendanceTime.isPresent() && !leaveTime.isPresent()
				&& status.isPresent()) {
			activityStatus.setActivity(status.get().getActivity());
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& !attendanceTime.isPresent() && !leaveTime.isPresent()
				&& !status.isPresent()) {
			activityStatus.setActivity(StatusClassfication.NOT_PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& leaveTime.isPresent() && leaveTime.get() <= timeNow) {
			activityStatus.setActivity(StatusClassfication.NOT_PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& attendanceTime.isPresent() && attendanceTime.get() <= timeNow
				&& goStraightAtr.isPresent() && goStraightAtr.get() == 1) {
			activityStatus.setActivity(StatusClassfication.GO_OUT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& attendanceTime.isPresent() && attendanceTime.get() <= timeNow
				&& goStraightAtr.isPresent() && goStraightAtr.get() == 1) {
			activityStatus.setActivity(StatusClassfication.GO_OUT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& attendanceTime.isPresent() && attendanceTime.get() <= timeNow
				&& goStraightAtr.isPresent() && goStraightAtr.get() != 1) {
			activityStatus.setActivity(StatusClassfication.NOT_PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& attendanceTime.isPresent() && attendanceTime.get() <= timeNow
				&& !goStraightAtr.isPresent()) {
			activityStatus.setActivity(StatusClassfication.NOT_PRESENT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& status.isPresent()) {
			activityStatus.setActivity(status.get().getActivity());
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& goStraightAtr.isPresent() && goStraightAtr.get() == 1) {
			activityStatus.setActivity(StatusClassfication.GO_OUT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 
				&& goStraightAtr.isPresent() && goStraightAtr.get() == 1) {
			activityStatus.setActivity(StatusClassfication.GO_OUT);
			return Optional.of(activityStatus);
		}
		
		if (workDivision.isPresent() && workDivision.get() == 0 ) {
			activityStatus.setActivity(StatusClassfication.PRESENT);
			return Optional.of(activityStatus);
		}
		
		return Optional.empty();
	}
	
	public interface Required {
	
	/* 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<社員の外出情報>
	 */
	public Optional<GoOutEmployeeInformation> getGoOutEmployeeInformation(String sid, GeneralDate date);
	
	/* @param sid  社員ID
	 * @param date 年月日>:
	 * @return Optional<ActivityStatus> Optional<在席のステータス>
	 */
	public Optional<ActivityStatus> getActivityStatus(String sid, GeneralDate date);
		
	}
}
