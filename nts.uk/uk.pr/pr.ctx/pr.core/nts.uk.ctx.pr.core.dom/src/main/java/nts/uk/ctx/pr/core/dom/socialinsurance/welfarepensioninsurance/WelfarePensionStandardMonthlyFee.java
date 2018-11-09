package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 厚生年金標準月額
 */
@Getter
public class WelfarePensionStandardMonthlyFee extends AggregateRoot {

    /**
     * 対象期間
     */
    private YearMonthPeriod targetPeriod;

    /**
     * 等級毎標準月額
     */
    private List<WelfarePensionStandardGradePerMonth> standardMonthlyPrice;

    /**
     * 厚生年金標準月額
     *
     * @param targetStartYm        対象期間
     * @param targetEndYm          対象期間
     * @param standardMonthlyPrice 等級毎標準月額
     */
    public WelfarePensionStandardMonthlyFee(int targetStartYm, int targetEndYm, List<WelfarePensionStandardGradePerMonth> standardMonthlyPrice) {
        this.targetPeriod = new YearMonthPeriod(new YearMonth(targetStartYm), new YearMonth(targetEndYm));
        this.standardMonthlyPrice = standardMonthlyPrice;
    }
}
