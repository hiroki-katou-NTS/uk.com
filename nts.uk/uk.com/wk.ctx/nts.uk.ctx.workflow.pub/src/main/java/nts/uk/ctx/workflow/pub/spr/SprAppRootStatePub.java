package nts.uk.ctx.workflow.pub.spr;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.spr.export.AppRootStateStatusSprExport;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface SprAppRootStatePub {

	public List<AppRootStateStatusSprExport> getStatusByEmpAndDate(String employeeID, GeneralDate startDate, GeneralDate endDate, Integer rootType);
	
}
