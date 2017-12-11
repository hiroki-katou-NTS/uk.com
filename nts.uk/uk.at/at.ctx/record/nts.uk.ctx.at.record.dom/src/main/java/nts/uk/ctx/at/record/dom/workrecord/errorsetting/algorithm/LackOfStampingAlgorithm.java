package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/*
 * 打刻漏れ
 */
@Stateless
public class LackOfStampingAlgorithm {
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;
	
	public OutPutProcess lackOfStamping(String companyID, String employeeID, GeneralDate processingDate, WorkInfoOfDailyPerformance workInfoOfDailyPerformance, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance){
		
		OutPutProcess outPutProcess = OutPutProcess.NO_ERROR;
		
		if(workInfoOfDailyPerformance == null){
			workInfoOfDailyPerformance = workInformationRepository.find(employeeID, processingDate).get();
		}
		
		WorkStyle workStyle = basicScheduleService
				.checkWorkDay(workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v());
		
		if (workStyle == WorkStyle.ONE_DAY_REST) {
			
			if (timeLeavingOfDailyPerformance == null) {
				timeLeavingOfDailyPerformance = timeLeavingOfDailyPerformanceRepository.findByKey(employeeID, processingDate).get();
			}
			
			List<TimeLeavingWork> timeLeavingWorkList = timeLeavingOfDailyPerformance.getTimeLeavingWorks();
			int leavingStampTime = 1;
			int attendanceStampTime = 1;
			List<Integer> attendanceItemID = new ArrayList<>();
			for(TimeLeavingWork timeLeavingWork : timeLeavingWorkList){
				WorkStamp leavingStamp = timeLeavingWork.getLeaveStamp().getStamp();
				WorkStamp attendanceStamp = timeLeavingWork.getAttendanceStamp().getStamp();
				// TODO - CreateEmployeeDailyPerError hoi? tuan'
				if (leavingStamp != null && attendanceStamp == null) {
					attendanceItemID.add(leavingStampTime);
					outPutProcess = createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, processingDate, new ErrorAlarmWorkRecordCode(SystemFixedErrorAlarm.S001.name()), attendanceItemID);
				} else if (leavingStamp == null && attendanceStamp != null) {
					
				} else if (leavingStamp == null && attendanceStamp == null) {
					
				}
				leavingStampTime ++;
				attendanceStampTime ++;
			}
		}
		
		return outPutProcess;
	}

}
