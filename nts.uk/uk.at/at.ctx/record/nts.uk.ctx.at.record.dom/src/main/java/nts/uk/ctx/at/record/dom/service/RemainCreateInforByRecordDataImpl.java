package nts.uk.ctx.at.record.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.VacationTimeInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class RemainCreateInforByRecordDataImpl implements RemainCreateInforByRecordData{
	@Inject
	private AttendanceTimeRepository attendanceRespo;
	@Inject
	private WorkInformationRepository workRespo;
	
	@Override
	public List<RecordRemainCreateInfor> lstRecordRemainData(String cid, String sid, DatePeriod dateData) {
		List<RecordRemainCreateInfor> lstOutputData = new ArrayList<>();
		//ドメインモデル「日別実績の勤怠時間」を取得する
		List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData =  attendanceRespo.findByPeriodOrderByYmd(sid, dateData);
		//ドメインモデル「日別実績の勤務情報」を取得する
		List<WorkInfoOfDailyPerformance> lstWorkInfor = workRespo.findByPeriodOrderByYmd(sid, dateData);
		if(lstAttendanceTimeData.isEmpty()
				|| lstWorkInfor.isEmpty()) {
			return lstOutputData;
		}
		//残数作成元情報を作成する
		return this.lstResult(lstAttendanceTimeData, lstWorkInfor);
	}
	
	private RecordRemainCreateInfor remainDataFromRecord(WorkInfoOfDailyPerformance workInfor, AttendanceTimeOfDailyPerformance attendanceInfor) {
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
		outputInfor.setLstVacationTimeInfor(this.getLstVacationTimeInfor());
		outputInfor.setSid(workInfor.getEmployeeId());
		outputInfor.setYmd(workInfor.getYmd());
		outputInfor.setWorkTypeCode(workInfor.getRecordInfo().getWorkTypeCode() == null ? "000" 
				: workInfor.getRecordInfo().getWorkTypeCode().v());
		outputInfor.setWorkTimeCode(Optional.of(workInfor.getRecordInfo().getSiftCode() == null ? "000" 
				: workInfor.getRecordInfo().getSiftCode().v()));
		return outputInfor;
	}
	/**
	 * データ移送元不明瞭とりあえず作成するだけ
	 * @return
	 */
	private List<VacationTimeInfor> getLstVacationTimeInfor(){
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

	@Override
	public List<RecordRemainCreateInfor> lstRecordRemainData(String cid, String sid, List<GeneralDate> dateData) {
		List<RecordRemainCreateInfor> lstOutputData = new ArrayList<>();
		//ドメインモデル「日別実績の勤怠時間」を取得する
		List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData = attendanceRespo.find(sid, dateData);
		//ドメインモデル「日別実績の勤務情報」を取得する
		List<WorkInfoOfDailyPerformance> lstWorkInfor = workRespo.findByListDate(sid, dateData);
		if(lstAttendanceTimeData.isEmpty()
				|| lstWorkInfor.isEmpty()) {
			return lstOutputData;
		}
		return this.lstResult(lstAttendanceTimeData, lstWorkInfor);
	}
	/**
	 * 残数作成元情報を作成する
	 * @param lstAttendanceTimeData
	 * @param lstWorkInfor
	 * @return
	 */
	private List<RecordRemainCreateInfor> lstResult(List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData,
			List<WorkInfoOfDailyPerformance> lstWorkInfor){
		List<RecordRemainCreateInfor> lstOutputData = new ArrayList<>();
		//残数作成元情報を作成する
		for (WorkInfoOfDailyPerformance workInfor : lstWorkInfor) {
			List<AttendanceTimeOfDailyPerformance> lstAttendance = lstAttendanceTimeData.stream()
					.filter(x -> x.getEmployeeId().equals(workInfor.getEmployeeId()) && x.getYmd().equals(workInfor.getYmd()))
					.collect(Collectors.toList());
			if(!lstAttendance.isEmpty()) {
				AttendanceTimeOfDailyPerformance attendanceInfor = lstAttendance.get(0);
				RecordRemainCreateInfor outPutData = this.remainDataFromRecord(workInfor, attendanceInfor);
				lstOutputData.add(outPutData);
			}
		}
		
		return lstOutputData;
	}
}
