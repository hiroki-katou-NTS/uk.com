package nts.uk.ctx.at.record.pubimp.workinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class RecordWorkInfoPubImpl implements RecordWorkInfoPub {

	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	/**
	 * Request List 5
	 */
	@Override
	public RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> opWorkInfoOfDailyPerformance = this.workInformationRepository.find(employeeId, ymd);
		if(!opWorkInfoOfDailyPerformance.isPresent()) {
			return new RecordWorkInfoPubExport("", "", null, null, null, null, null, null, null, null, null);
		}
		
		// 日別実績の勤務情報
		WorkInfoOfDailyPerformance workInfoOfDailyPerformance = opWorkInfoOfDailyPerformance.get();
		
		//日別実績の出退勤
		Optional<TimeLeavingOfDailyPerformance> opTimeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository.findByKey(employeeId, ymd);
		if(!opTimeLeavingOfDailyPerformance.isPresent()) {
			return new RecordWorkInfoPubExport("", "", null, null, null, null, null, null, null, null, null);
		}
		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = opTimeLeavingOfDailyPerformance.get();
		
		Integer attendanceStampTimeFirst = -1;
		Integer leaveStampTimeFirst = -1;
		Integer attendanceStampTimeSecond = -1;
		Integer leaveStampTimeSecond = -1; 
		if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().size()>1){
			// nampt : check null case
			if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().isPresent() &&
					timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().get().getStamp().isPresent()){
						attendanceStampTimeSecond = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp()
								.map(x -> x.getStamp().map(y -> {
									if(y.getTimeWithDay()==null){
										return null;
									} else {
										return y.getTimeWithDay().v();
									}
								}).orElse(null)).orElse(null);
			}
			// nampt : check null case
			if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().isPresent() &&
					timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().get().getStamp().isPresent()){
				leaveStampTimeSecond = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp()
						.map(x -> x.getStamp().map(y -> {
							if(y.getTimeWithDay()==null){
								return null;
							} else {
								return y.getTimeWithDay().v();
							}
						}).orElse(null)).orElse(null);
			}			
		}
		if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().size()>0){
			// nampt : check null case
			if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().isPresent() &&
					timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().get().getStamp().isPresent()){
				attendanceStampTimeFirst = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp()
						.map(x -> x.getStamp().map(y -> {
							if(y.getTimeWithDay()==null){
								return null;
							} else {
								return y.getTimeWithDay().v();
							}
						}).orElse(null)).orElse(null);
			}
			// nampt : check null case
			if(timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().isPresent() &&
					timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().get().getStamp().isPresent()){
				leaveStampTimeFirst = timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp()
						.map(x -> x.getStamp().map(y -> {
							if(y.getTimeWithDay()==null){
								return null;
							} else {
								return y.getTimeWithDay().v();
							}
						}).orElse(null)).orElse(null);
			}			
		}
		
		RecordWorkInfoPubExport recordWorkInfoPubExport = new RecordWorkInfoPubExport(
				workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v(),
				workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTimeCode().v(),
				attendanceStampTimeFirst,
				leaveStampTimeFirst,
				attendanceStampTimeSecond,
				leaveStampTimeSecond,
				null, null, null, null, null);
		return recordWorkInfoPubExport;
	}

}
