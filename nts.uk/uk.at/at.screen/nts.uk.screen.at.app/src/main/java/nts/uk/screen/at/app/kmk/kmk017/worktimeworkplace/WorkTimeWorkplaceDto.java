package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeWorkplaceDto {

    /**
     * 職場ID
     */
    private String workplaceID;

    /**
     * 利用就業時間帯
     */
    private List<String> workTimeCodes;

    public static WorkTimeWorkplaceDto setData(WorkTimeWorkplace data) {
        return new WorkTimeWorkplaceDto(
            data.getWorkplaceID(),
            data.getWorkTimeCodes().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())
        );
    }
}
