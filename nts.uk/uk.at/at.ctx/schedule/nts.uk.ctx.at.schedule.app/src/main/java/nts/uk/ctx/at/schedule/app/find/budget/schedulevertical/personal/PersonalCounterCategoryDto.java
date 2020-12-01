package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCounterCategoryDto {

    private int value;
    private boolean use;
    private Boolean setting;

    public static List<PersonalCounterCategoryDto> setData(List<EnumConstant> listEnum, Optional<PersonalCounter> PersonalCounter,
                                                           Boolean timesNumber1,Boolean timesNumber2,Boolean timesNumber3) {
        return listEnum.stream().map(x -> {
            return new PersonalCounterCategoryDto(
                x.getValue(),
                PersonalCounter.isPresent() && PersonalCounter.get().isUsed(PersonalCounterCategory.of(x.getValue())),
                x.getValue() == 7 ? timesNumber1 : x.getValue() == 8 ? timesNumber2 : x.getValue() == 9 ? timesNumber3 : null
            );
        }).sorted(Comparator.comparing(PersonalCounterCategoryDto::getValue)).collect(Collectors.toList());
    }
}
