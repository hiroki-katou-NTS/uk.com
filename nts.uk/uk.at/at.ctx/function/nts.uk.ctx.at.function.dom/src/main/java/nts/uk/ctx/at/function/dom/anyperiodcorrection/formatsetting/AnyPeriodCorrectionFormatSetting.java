package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.実績修正.任意期間修正.フォーマット設定
 * 任意期間修正のフォーマット設定
 */
@Getter
@AllArgsConstructor
public class AnyPeriodCorrectionFormatSetting extends AggregateRoot {
    /**コード*/
    private MonthlyPerformanceFormatCode code;
    /**名称*/
    private MonPfmCorrectionFormatName name;
    /**シート設定*/
    private MonthlyActualResults sheetSetting;

    public AnyPeriodCorrectionFormatSetting(String code, String name, List<SheetCorrectedMonthly> listSheet) {
        this.code = new MonthlyPerformanceFormatCode(code);
        this.name = new MonPfmCorrectionFormatName(name);
        this.sheetSetting = new MonthlyActualResults(listSheet);
    }
}
