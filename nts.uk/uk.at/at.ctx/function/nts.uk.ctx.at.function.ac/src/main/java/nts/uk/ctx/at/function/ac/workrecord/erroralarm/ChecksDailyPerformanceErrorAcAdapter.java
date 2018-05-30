package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror.ChecksDailyPerformanceErrorRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.CheckDailyPerformanceErrorPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.DailyPerformanceErrorExport;

@Stateless
public class ChecksDailyPerformanceErrorAcAdapter implements ChecksDailyPerformanceErrorRepository{

	@Inject
	private CheckDailyPerformanceErrorPub checkDailyPerformanceError;
	
	@Override
	public boolean checked(String employeeID, GeneralDate strDate, GeneralDate endDate) {
		List<DailyPerformanceErrorExport> dailyErrorExport = checkDailyPerformanceError.check(employeeID, strDate, endDate);
		for (DailyPerformanceErrorExport item : dailyErrorExport) {
			if(item.isError()) {
				return true;
			}
		}
		return false;
	}

}
