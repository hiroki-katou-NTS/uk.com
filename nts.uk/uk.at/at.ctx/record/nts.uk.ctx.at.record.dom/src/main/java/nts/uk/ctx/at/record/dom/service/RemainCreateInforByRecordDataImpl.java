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
	@Inject
	private RemainNumberCreateInformation remainInfor;
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
		return remainInfor.createRemainInfor(lstAttendanceTimeData, lstWorkInfor);
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
		return remainInfor.createRemainInfor(lstAttendanceTimeData, lstWorkInfor);
	}
	
}
