package nts.uk.ctx.at.shared.dom.worktime.workplace;
/**
 * @author tutk
 */

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

public interface WorkTimeWorkplaceRepository {

    List<WorkTimeSetting> getWorkTimeWorkplaceById(String companyID, String workplaceID);

    void add(WorkTimeWorkplace domain);

    void update(WorkTimeWorkplace domain);

    void remove(WorkTimeWorkplace domain);

    List<WorkTimeWorkplace> getByCId(String companyID);

    Optional<WorkTimeWorkplace> getByCIdAndWkpId(String companyIDcompanyID, String workplaceID);

}
