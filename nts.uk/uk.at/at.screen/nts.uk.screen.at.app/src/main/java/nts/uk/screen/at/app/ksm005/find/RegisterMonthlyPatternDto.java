package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegisterMonthlyPatternDto {

    private List<MonthlyPatternDto> monthlyPatternDto;

    private List<MonthlyPattern> workMonthlySettings;
}
