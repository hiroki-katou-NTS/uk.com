package nts.uk.ctx.at.shared.dom.worktime.workplace;
/**
 * 
 * @author tutk
 *
 */

import java.util.List;

public interface WorkTimeWorkplaceRepository {
	List<String> getWorkTimeWorkplaceById(String companyID,String workplaceID);
}
