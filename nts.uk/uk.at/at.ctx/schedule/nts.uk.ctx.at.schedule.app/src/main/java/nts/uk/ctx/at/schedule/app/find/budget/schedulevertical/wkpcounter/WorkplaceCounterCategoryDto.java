package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpcounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceCounterCategoryDto {

    private int value;
    private boolean use;

    public static List<WorkplaceCounterCategoryDto> setData(List<EnumConstant> listEnum,WorkplaceCounter workplaceCounter) {
        return listEnum.stream().map(x -> {
            return new WorkplaceCounterCategoryDto(
                x.getValue(),
                workplaceCounter.isUsed(WorkplaceCounterCategory.of(x.getValue()))
            );
        }).sorted(Comparator.comparing(WorkplaceCounterCategoryDto::getValue)).collect(Collectors.toList());
    }
}
