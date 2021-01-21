package nts.uk.ctx.at.schedule.app.query.MonthlyPattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatternDto {
    /** The company id. */
    //会社ID
    private String companyId;

    /** The monthly pattern code. */
    //月間パターンコード
    private String monthlyPatternCode;

    /** The monthly pattern name. */
    // 月間パターン名称
    private String monthlyPatternName;
}
