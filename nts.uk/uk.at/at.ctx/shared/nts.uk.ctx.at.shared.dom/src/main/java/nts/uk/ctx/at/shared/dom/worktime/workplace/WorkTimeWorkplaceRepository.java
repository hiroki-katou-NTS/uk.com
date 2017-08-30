package nts.uk.ctx.at.shared.dom.worktime.workplace;
/**
 * 
 * @author tutk
 *
 */

import java.util.Optional;

public interface WorkTimeWorkplaceRepository {
	Optional<WorkTimeWorkplace> getWorkTimeWorkplaceById(String companyID,String workplaceID,String workTimeId);
}
