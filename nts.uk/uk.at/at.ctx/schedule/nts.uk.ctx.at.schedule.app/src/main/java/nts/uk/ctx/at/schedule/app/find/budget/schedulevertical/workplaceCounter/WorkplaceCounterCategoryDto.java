package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.workplaceCounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceCounterCategoryDto {

    private int value;
    private boolean use;

    public static List<WorkplaceCounterCategoryDto> setData(WorkplaceCounter workplaceCounter) {

        return workplaceCounter.getUseCategories().stream().map(x -> new WorkplaceCounterCategoryDto(x.value, workplaceCounter.isUsed(x))).collect(Collectors.toList());
    }

}
