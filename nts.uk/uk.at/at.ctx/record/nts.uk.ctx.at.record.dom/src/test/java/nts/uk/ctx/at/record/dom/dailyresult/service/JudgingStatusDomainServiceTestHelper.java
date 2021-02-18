package nts.uk.ctx.at.record.dom.dailyresult.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JudgingStatusDomainServiceTestHelper {
	
	/**
	 * Mock [R-1]日別実績の勤務情報を取得する
	 * 
	 * @param attr
	 * @return
	 */
	public static Optional<WorkInfoOfDailyPerformance> getR1Result(NotUseAttribute attr) {
		WorkInformation workInformation = new WorkInformation("001", "002");

		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance();
		workInfoOfDailyAttendance.setRecordInfo(workInformation);
		workInfoOfDailyAttendance.setGoStraightAtr(attr);

		WorkInfoOfDailyPerformance WorkInfoOfDailyPerformance = new WorkInfoOfDailyPerformance();
		WorkInfoOfDailyPerformance.setWorkInformation(workInfoOfDailyAttendance);

		return Optional.ofNullable(WorkInfoOfDailyPerformance);
	}

	/**
	 * Mock [R-2] 日別実績の出退勤を取得する
	 * 
	 * @param nowAttendance
	 * @param nowLeave
	 * @param workNo
	 * @return
	 */
	public static Optional<TimeLeavingOfDailyPerformance> getR2Result(Integer nowAttendance, Integer nowLeave, Integer workNo) {
		WorkTimeInformation workTimeInformationAttendance = new WorkTimeInformation();
		workTimeInformationAttendance
				.setTimeWithDay(Optional.ofNullable(nowAttendance == null ? null : new TimeWithDayAttr(nowAttendance)));

		WorkStamp workStampAttendance = new WorkStamp();
		workStampAttendance.setTimeDay(workTimeInformationAttendance);

		TimeActualStamp timeActualStampAttendance = new TimeActualStamp();
		timeActualStampAttendance.setStamp(Optional.ofNullable(workStampAttendance));

		WorkTimeInformation workTimeInformationLeave = new WorkTimeInformation();
		workTimeInformationLeave
				.setTimeWithDay(Optional.ofNullable(nowLeave == null ? null : new TimeWithDayAttr(nowLeave)));

		WorkStamp workStampLeave = new WorkStamp();
		workStampLeave.setTimeDay(workTimeInformationLeave);

		TimeActualStamp timeActualStampLeave = new TimeActualStamp();
		timeActualStampLeave.setStamp(Optional.ofNullable(workStampLeave));

		TimeLeavingWork timeLeavingWork1 = new TimeLeavingWork();
		timeLeavingWork1.setWorkNo(new WorkNo(workNo));
		timeLeavingWork1.setAttendanceStamp(Optional.ofNullable(timeActualStampAttendance));
		timeLeavingWork1.setLeaveStamp(Optional.ofNullable(timeActualStampLeave));
		
		TimeLeavingOfDailyAttd timeLeave = new TimeLeavingOfDailyAttd();
		List<TimeLeavingWork> list = new ArrayList<>();
		list.add(timeLeavingWork1);
		timeLeave.setTimeLeavingWorks(list);
		timeLeave.setWorkTimes(new WorkTimes(1));

		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = new TimeLeavingOfDailyPerformance();
		timeLeavingOfDailyPerformance.setEmployeeId("employeeId");
		timeLeavingOfDailyPerformance.setYmd(GeneralDate.today());
		timeLeavingOfDailyPerformance.setAttendance(timeLeave);

		return Optional.ofNullable(timeLeavingOfDailyPerformance);
	}
	
	/**
	 * Mock [R-4] 勤務種類を取得する
	 * 
	 * @param workTypeUnit
	 * @param oneDay
	 * @param morning
	 * @param afternoon
	 * @return
	 */
	public static Optional<WorkType> getR4Result(WorkTypeUnit workTypeUnit, WorkTypeClassification oneDay,
			WorkTypeClassification morning, WorkTypeClassification afternoon) {
		DailyWork dailyWork = new DailyWork();
		dailyWork.setWorkTypeUnit(workTypeUnit);
		dailyWork.setOneDay(oneDay);
		dailyWork.setMorning(morning);
		dailyWork.setAfternoon(afternoon);

		WorkType workType = new WorkType();
		workType.setWorkTypeCode(new WorkTypeCode("001"));
		workType.setDailyWork(dailyWork);
		return Optional.ofNullable(workType);
	}
	
	public static AttendanceAccordActualData case1Expected() {
		return buildData(StatusClassfication.GO_HOME, false);
	}
	
	public static AttendanceAccordActualData case2Expected() {
		return buildData(StatusClassfication.GO_OUT, false);
	}
	
	public static AttendanceAccordActualData case3Expected() {
		return buildData(StatusClassfication.PRESENT, false);
	}
	
	public static AttendanceAccordActualData case4Expected() {
		return buildData(StatusClassfication.NOT_PRESENT, false);
	}
	
	public static AttendanceAccordActualData case5Expected() {
		return buildData(StatusClassfication.NOT_PRESENT, false);
	}
	
	public static AttendanceAccordActualData case6Expected() {
		return buildData(StatusClassfication.GO_OUT, false);
	}
	
	public static AttendanceAccordActualData case7Expected() {
		return buildData(StatusClassfication.PRESENT, false);
	}
	
	public static AttendanceAccordActualData case8Expected() {
		return buildData(StatusClassfication.NOT_PRESENT, false);
	}
	
	public static AttendanceAccordActualData case9Expected() {
		return buildData(StatusClassfication.NOT_PRESENT, false);
	}
	
	public static AttendanceAccordActualData case10Expected() {
		return buildData(StatusClassfication.NOT_PRESENT, true);
	}
	
	public static AttendanceAccordActualData case11Expected() {
		return buildData(StatusClassfication.HOLIDAY, false);
	}
	
	private static AttendanceAccordActualData buildData(StatusClassfication statusClassfication, boolean workingNow) {
		return AttendanceAccordActualData.builder()
				.attendanceState(statusClassfication)
				.workingNow(workingNow)
				.build();
	}
}