package nts.uk.screen.at.app.workrule.closure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 現在の締め期間
 *
 * @author Le Huu Dat
 */
@AllArgsConstructor
@Getter
public class CurrentClosurePeriod {
    /**
     * 締めＩＤ
     */
    private Integer closureId;

    /**
     * 処理年月
     */
    private YearMonth processingYm;

    /**
     * 締め終了日
     */
    private GeneralDate closureStartDate;

    /**
     * 締め開始日
     */
    private GeneralDate closureEndDate;
}
