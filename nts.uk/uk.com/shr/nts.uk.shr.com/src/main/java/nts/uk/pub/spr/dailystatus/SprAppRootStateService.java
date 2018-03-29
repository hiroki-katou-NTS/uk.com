package nts.uk.pub.spr.dailystatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.dailystatus.output.AppRootStateStatusSpr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface SprAppRootStateService {

	public List<AppRootStateStatusSpr> getStatusByEmpAndDate(String employeeID, GeneralDate startDate, GeneralDate endDate, Integer rootType);
	
}
