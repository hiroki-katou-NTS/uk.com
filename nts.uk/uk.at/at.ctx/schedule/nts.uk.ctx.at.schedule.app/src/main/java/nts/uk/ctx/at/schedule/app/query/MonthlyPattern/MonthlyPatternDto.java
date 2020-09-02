package nts.uk.ctx.at.schedule.app.query.MonthlyPattern;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;

import java.util.List;

@Getter
@Setter
public class MonthlyPatternDto {
   public List<PatternDto> listMonthlyPattern;
}
