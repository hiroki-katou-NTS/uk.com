package nts.uk.screen.at.app.ksm003.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.i18n.TextResource;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCyleInfoDto {

    private String typeCode;

    private String typeName;

    private String timeCode;

    private String timeName;

    private int days;

    private int dispOrder;

    public WorkCyleInfoDto(String typeCode, String timeCode, int days, int dispOrder, List<WorkType> workTypes, List<WorkTimeSetting> workTimeItems) {
        this.typeCode = typeCode;
        this.timeCode = timeCode;
        this.days = days;
        this.dispOrder = dispOrder;

        // Get WorkType
        val workType = workTypes.stream().filter(i -> i.getWorkTypeCode().v().equals(typeCode)).findFirst();
        this.typeName = workType.isPresent() ? workType.get().getName().v() : TextResource.localize("KSM003_2");

        if (timeCode != null) {
            // Get WorkTime
            val workTime = workTimeItems.stream().filter(i -> i.getWorktimeCode().v().equals(timeCode)).findFirst();
            this.timeCode = workTime.isPresent() ? workTime.get().getWorkTimeDisplayName().getWorkTimeName().v() : TextResource.localize("KSM003_2");
        }

    }
}
