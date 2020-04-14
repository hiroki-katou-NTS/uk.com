package nts.uk.ctx.at.shared.dom.worktime.workplace;
/**
 * 
 * @author tutk
 *
 */

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

public interface WorkTimeWorkplaceRepository {
	List<WorkTimeSetting> getWorkTimeWorkplaceById(String companyID,String workplaceID);
}
