package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;

/**
 * 
 * @author nampt
 * 2重打刻
 * 	
 */
@Stateless
public class DoubleStampAlgorithm {	
	
	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;
	
	public void doubleStamp (String companyID, String employeeID, GeneralDate processingDate, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance){
//		OutPutProcess outPutProcess = OutPutProcess.NO_ERROR;
		
		List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();
		
		for (TimeLeavingWork timeLeavingWork : timeLeavingWorks){
			// 出勤の二重打刻チェック処理
			TimeActualStamp attendanceTimeActual = timeLeavingWork.getAttendanceStamp();
			OutPutProcess outPutAttendance = this.doubleStampCheckProcessing(companyID, employeeID, processingDate, attendanceTimeActual);
			
			// 退勤の二重打刻チェック処理
			TimeActualStamp leavingTimeActual = timeLeavingWork.getLeaveStamp();
			OutPutProcess outPutLeaving = this.doubleStampCheckProcessing(companyID, employeeID, processingDate, leavingTimeActual);
		}
		
//		return outPutProcess;
	}
	
	private OutPutProcess doubleStampCheckProcessing(String companyID, String employeeID, GeneralDate processingDate, TimeActualStamp timeActualStamp){
		OutPutProcess outPutCheckProcessing = OutPutProcess.NO_ERROR;
		
		List<Integer> attendanceItemIDs = new ArrayList<>();
		
		if (timeActualStamp.getNumberOfReflectionStamp() < 2) {
			return outPutCheckProcessing; 
		} else {
			OutPutProcess outPut = createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, 
					employeeID, processingDate,
					new ErrorAlarmWorkRecordCode("S006"), attendanceItemIDs);
			outPutCheckProcessing = OutPutProcess.HAS_ERROR;
		}
		return outPutCheckProcessing;
	}

}
