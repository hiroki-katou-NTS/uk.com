package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 総合計の表示設定
 */
@AllArgsConstructor
@Getter
public class GrandTotalDisplaySetting {
    /**
     * 総合計を表示する
     */
    private NotUseAtr displayGrandTotal;

    /**
     * 職場・応援計を表示する
     */
    private NotUseAtr displayWorkplaceSupportMeter;
}
