package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.実績修正.任意期間修正.フォーマット設定.列幅設定
 * 任意期間修正のグリッド列幅
 */
@Getter
@AllArgsConstructor
public class AnyPeriodCorrectionGridColumnWidth extends AggregateRoot {
    /**社員ID*/
    private String employeeId;
    /**列幅*/
    private List<DisplayItemColumnWidth> columnWidths;
}
