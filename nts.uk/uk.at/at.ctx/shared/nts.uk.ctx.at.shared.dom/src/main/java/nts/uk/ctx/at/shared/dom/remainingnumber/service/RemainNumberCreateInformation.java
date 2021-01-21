package nts.uk.ctx.at.shared.dom.remainingnumber.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.VacationTimeInfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;

public class RemainNumberCreateInformation {
	
	/**
	 * 残数作成元情報を作成する
	 * @param lstAttendanceTimeData 日別実績の勤怠時間
	 * @param lstWorkInfor 日別実績の勤務情報
	 * @return
	 */
	public static List<RecordRemainCreateInfor> createRemainInfor(String employeeId,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> lstAttendanceTimeData,
			Map<GeneralDate, WorkInfoOfDailyAttendance> lstWorkInfor) {
		List<RecordRemainCreateInfor> lstOutputData = new ArrayList<>();
		//残数作成元情報を作成する
		lstWorkInfor.entrySet().stream().forEach(workInfor -> {
			val attendanceInfor = lstAttendanceTimeData.get(workInfor.getKey());
			if(attendanceInfor != null) {
				RecordRemainCreateInfor outPutData = remainDataFromRecord(employeeId, 
						workInfor.getKey(), workInfor.getValue(), attendanceInfor);
				lstOutputData.add(outPutData);
			}
		});		
		return lstOutputData;
	}
	
	/**
	 * 
	 * @param workInfor
	 * @param attendanceInfor
	 * @return
	 */
	public static RecordRemainCreateInfor remainDataFromRecord(String employeeId,
			GeneralDate ymd, WorkInfoOfDailyAttendance workInfor,
			AttendanceTimeOfDailyAttendance attendanceInfor) {
		RecordRemainCreateInfor outputInfor = new RecordRemainCreateInfor();
		//残業振替時間の合計を算出する
		Optional<OverTimeOfDaily> overTimeWork = attendanceInfor.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork();
		Integer overTimes = 0;
		if(overTimeWork.isPresent()) {
			List<OverTimeFrameTime> overTimeWorkFrameTime = overTimeWork.get().getOverTimeWorkFrameTime();
			for (OverTimeFrameTime overTimeFrameTime : overTimeWorkFrameTime) {
				overTimes += overTimeFrameTime.getTransferTime().getTime().v();
			}
		} 
		outputInfor.setTransferOvertimesTotal(overTimes);
		//休出振替時間の合計を算出する
		Optional<HolidayWorkTimeOfDaily> workHolidayTime = attendanceInfor.getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime();
		Integer transferTotal = 0;
		if(workHolidayTime.isPresent()) {
			List<HolidayWorkFrameTime> holidayWorkFrameTime = workHolidayTime.get().getHolidayWorkFrameTime();			
			for (HolidayWorkFrameTime holidayWork : holidayWorkFrameTime) {
				transferTotal += holidayWork.getTransferTime().isPresent() ? holidayWork.getTransferTime().get().getTime().v() : 0;
			}
		}
		outputInfor.setTransferTotal(transferTotal);
		//時間休暇使用情報を作成する
		outputInfor.setLstVacationTimeInfor(getLstVacationTimeInfor());
		outputInfor.setSid(employeeId);
		outputInfor.setYmd(ymd);
		outputInfor.setWorkTypeCode(workInfor.getRecordInfo().getWorkTypeCode() == null ? "" 
				: workInfor.getRecordInfo().getWorkTypeCode().v());
		outputInfor.setWorkTimeCode(Optional.of(workInfor.getRecordInfo().getWorkTimeCode() == null ? "" 
				: workInfor.getRecordInfo().getWorkTimeCode().v()));
		return outputInfor;
	}
	
	/**
	 * データ移送元不明瞭とりあえず作成するだけ
	 * @return
	 */
	private static List<VacationTimeInfor> getLstVacationTimeInfor(){
		List<VacationTimeInfor> lstOutput = new ArrayList<>();
		VacationTimeInfor timeInfor = new VacationTimeInfor(0, AppTimeType.ATWORK, 0, 0);
		lstOutput.add(timeInfor);
		timeInfor = new VacationTimeInfor(0, AppTimeType.ATWORK2, 0, 0);
		lstOutput.add(timeInfor);
		timeInfor = new VacationTimeInfor(0, AppTimeType.OFFWORK, 0, 0);
		lstOutput.add(timeInfor);
		timeInfor = new VacationTimeInfor(0, AppTimeType.OFFWORK2, 0, 0);
		lstOutput.add(timeInfor);
		timeInfor = new VacationTimeInfor(0, AppTimeType.PRIVATE, 0, 0);
		lstOutput.add(timeInfor);
		timeInfor = new VacationTimeInfor(0, AppTimeType.UNION, 0, 0);
		lstOutput.add(timeInfor);
		return lstOutput;
	}
}
