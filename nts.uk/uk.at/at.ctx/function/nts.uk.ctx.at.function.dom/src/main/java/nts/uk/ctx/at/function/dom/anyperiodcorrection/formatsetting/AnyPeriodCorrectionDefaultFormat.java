package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.実績修正.任意期間修正.フォーマット設定
 * 任意期間修正の規定フォーマット
 */
@Getter
@AllArgsConstructor
public class AnyPeriodCorrectionDefaultFormat extends AggregateRoot {
    /**コード*/
    private MonthlyPerformanceFormatCode code;

    public AnyPeriodCorrectionDefaultFormat(String code) {
        this.code = new MonthlyPerformanceFormatCode(code);
    }
}
