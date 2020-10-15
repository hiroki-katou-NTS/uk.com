package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class MonthlySettingPatternDto {

    private List<MonthlyPatternDto> monthlyPatternDtos;

    private List<Integer> listMonthYear;

}
