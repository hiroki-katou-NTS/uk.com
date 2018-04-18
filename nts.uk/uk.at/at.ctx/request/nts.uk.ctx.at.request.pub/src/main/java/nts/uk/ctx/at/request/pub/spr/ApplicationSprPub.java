package nts.uk.ctx.at.request.pub.spr;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.pub.spr.export.AppOverTimeSprExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationSprPub {
	
	public Optional<AppOverTimeSprExport> getAppOvertimeByDate(GeneralDate appDate, String employeeID, Integer overTimeAtr);
	
}
