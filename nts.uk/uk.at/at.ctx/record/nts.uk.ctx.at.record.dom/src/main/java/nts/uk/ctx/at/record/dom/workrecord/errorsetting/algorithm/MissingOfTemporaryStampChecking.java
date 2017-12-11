package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.errorsetting.OutPutProcess;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;

@Stateless
public class MissingOfTemporaryStampChecking {

	public OutPutProcess missingOfTemporaryStampChecking(
			TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {
		OutPutProcess outPutProcess = OutPutProcess.NO_ERROR;

		List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();

		for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
			if (timeLeavingWork.getAttendanceStamp() == null || (timeLeavingWork.getAttendanceStamp() != null
					&& timeLeavingWork.getAttendanceStamp().getStamp() == null)) {

			}
		}

		return outPutProcess;
	}
}
