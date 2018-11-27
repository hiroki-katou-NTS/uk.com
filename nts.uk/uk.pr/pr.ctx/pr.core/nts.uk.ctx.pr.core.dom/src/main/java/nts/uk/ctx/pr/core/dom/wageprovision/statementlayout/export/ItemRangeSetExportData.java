package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;

@NoArgsConstructor
@Getter
@Setter
public class ItemRangeSetExportData {

    /**
     * エラー上限値設定
     */
    private UseRangeAtr errorUpperSettingAtr;

    /**
     * エラー下限値設定
     */
    private UseRangeAtr errorLowerSettingAtr;

    /**
     * アラーム上限値設定
     */
    private UseRangeAtr alarmUpperSettingAtr;

    /**
     * アラーム下限値設定
     */
    private UseRangeAtr alarmLowerSettingAtr;
}
