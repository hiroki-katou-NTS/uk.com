package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 職場合計の表示設定
 */
@AllArgsConstructor
@Getter
public class WorkplaceTotalDisplaySetting {
    /**
     * 1日合計を表示する
     */
    private NotUseAtr displayOneDayTotal;

    /**
     * 応援内訳を表示する
     */
    private NotUseAtr displaySupportDetail;

    /**
     * 職場・応援計を表示する
     */
    private NotUseAtr displayWorkplaceSupportMeter;

    /**
     * 職場計を表示する
     */
    private NotUseAtr displayWorkplaceTotal;
}
