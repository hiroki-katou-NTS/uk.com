package nts.uk.ctx.at.record.pubimp.workinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;

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
		Optional<WorkInfoOfDailyPerformance> otpWorkInfoOfDailyPerformance = this.workInformationRepository.find(employeeId, ymd);
		if(!otpWorkInfoOfDailyPerformance.isPresent()) {
			return new RecordWorkInfoPubExport("", "", -1, -1, -1, -1, -1, -1, -1, -1, -1);
		}
		
		// 日別実績の勤務情報
		WorkInfoOfDailyPerformance workInfoOfDailyPerformance = otpWorkInfoOfDailyPerformance.get();
		
		//日別実績の出退勤
		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository.findByKey(employeeId, ymd).get();
		
		RecordWorkInfoPubExport recordWorkInfoPubExport = new RecordWorkInfoPubExport(
				workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v(),
				workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTimeCode().v(),
				timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getAttendanceStamp().getStamp().getTimeWithDay().v(),
				timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(0).getLeaveStamp().getStamp().getTimeWithDay().v(),
				timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getAttendanceStamp().getStamp().getTimeWithDay().v(),
				timeLeavingOfDailyPerformance.getTimeLeavingWorks().get(1).getLeaveStamp().getStamp().getTimeWithDay().v(),
				-1, -1, -1, -1, -1);
		return recordWorkInfoPubExport;
	}

}
