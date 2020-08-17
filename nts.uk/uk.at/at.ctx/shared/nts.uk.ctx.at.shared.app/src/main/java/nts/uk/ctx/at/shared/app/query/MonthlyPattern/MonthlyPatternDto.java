package nts.uk.ctx.at.shared.app.query.MonthlyPattern;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

import java.util.List;

@Getter
@Setter
public class MonthlyPatternDto {
   public List<MonthlyPattern> listMonthlyPattern;
}
