package nts.uk.pub.spr.appstatus;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.appstatus.output.AppOverTimeSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationSprService {
	
	public Optional<AppOverTimeSpr> getAppOvertimeByDate(GeneralDate appDate, String employeeID, Integer overTimeAtr);
	
}
