package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.service.WorkTimeWorkplaveSevice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 職場リストを表示する
 */
@Stateless
public class WorkTimeWorkplaceProcessor {

    @Inject
    private WorkTimeWorkplaveSevice workTimeWorkplaveSevice;

    public List<WorkTimeWorkplaceDto> findAgreeTimeOfCompany() {

        List<WorkTimeWorkplace> workTimeWorkplaces = workTimeWorkplaveSevice.getByCid();
        return workTimeWorkplaces.stream().map(WorkTimeWorkplaceDto::setData).collect(Collectors.toList());
    }
}
