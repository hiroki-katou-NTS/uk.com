package nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror;

import nts.arc.time.GeneralDate;

public interface ChecksDailyPerformanceErrorRepository {

	boolean checked(String employeeID, GeneralDate strDate, GeneralDate endDate);
}
