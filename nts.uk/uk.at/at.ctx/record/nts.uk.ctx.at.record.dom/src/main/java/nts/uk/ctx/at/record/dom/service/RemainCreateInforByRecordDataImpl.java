package nts.uk.ctx.at.record.dom.service;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.service.RemainNumberCreateInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
@Stateless
public class RemainCreateInforByRecordDataImpl implements RemainCreateInforByRecordData{
	@Inject
	private AttendanceTimeRepository attendanceRespo;
	@Inject
	private WorkInformationRepository workRespo;
	@Override
	public List<RecordRemainCreateInfor> lstRecordRemainData(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData) {
		//ドメインモデル「日別実績の勤怠時間」を取得する
		List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData =  attendanceRespo.findByPeriodOrderByYmd(sid, dateData);
		//ドメインモデル「日別実績の勤務情報」を取得する
		List<WorkInfoOfDailyPerformance> lstWorkInfor = workRespo.findByPeriodOrderByYmd(sid, dateData);
		//残数作成元情報を作成する
		return RemainNumberCreateInformation.createRemainInfor(sid,
				lstAttendanceTimeData.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime())), 
				lstWorkInfor.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getWorkInformation())));
	}
	
	
	@Override
	public List<RecordRemainCreateInfor> lstRecordRemainData(CacheCarrier cacheCarrier, String cid, String sid, List<GeneralDate> dateData) {
		//ドメインモデル「日別実績の勤怠時間」を取得する
		List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData = attendanceRespo.find(sid, dateData);
		//ドメインモデル「日別実績の勤務情報」を取得する
		List<WorkInfoOfDailyPerformance> lstWorkInfor = workRespo.findByListDate(sid, dateData);	
		
		return RemainNumberCreateInformation.createRemainInfor(sid,
				lstAttendanceTimeData.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime())), 
				lstWorkInfor.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getWorkInformation())));
	}
	
}
