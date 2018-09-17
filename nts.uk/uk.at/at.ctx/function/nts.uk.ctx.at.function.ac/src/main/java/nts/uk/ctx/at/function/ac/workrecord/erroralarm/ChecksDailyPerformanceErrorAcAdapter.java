package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror.ChecksDailyPerformanceErrorRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.CheckDailyPerformanceErrorPub;

@Stateless
public class ChecksDailyPerformanceErrorAcAdapter implements ChecksDailyPerformanceErrorRepository{

	@Inject
	private CheckDailyPerformanceErrorPub checkDailyPerformanceError;
	
	@Override
	public boolean checked(String employeeID, GeneralDate strDate, GeneralDate endDate) {
		return checkDailyPerformanceError.checksDailyPerformanceError(employeeID, strDate, endDate);
	}

}
