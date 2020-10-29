package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personalCounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCounterCategoryDto {

    private int value;
    private boolean use;

    public static List<PersonalCounterCategoryDto> setData(PersonalCounter PersonalCounter){

        return PersonalCounter.getUseCategories().stream().map(x -> new PersonalCounterCategoryDto(x.value, PersonalCounter.isUsed(x))).collect(Collectors.toList());
    }

}
