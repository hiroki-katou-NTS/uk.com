package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

import java.util.List;

@Getter
@AllArgsConstructor
public class MonthlySettingPatternDto {

    private List<MonthlyPatternDto> monthlyPatternDtos;

    private List<GeneralDate> listMonthYear;

}
