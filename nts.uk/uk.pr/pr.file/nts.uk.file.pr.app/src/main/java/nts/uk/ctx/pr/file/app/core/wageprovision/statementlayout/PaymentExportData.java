package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.TaxAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentCaclMethodAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentProportionalAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentTotalObjAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.WorkingAtr;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class PaymentExportData {

    /**
     * 合計対象
     */
    private PaymentTotalObjAtr totalObj;

    /**
     * 計算方法
     */
    private PaymentCaclMethodAtr calcMethod;

    /**
     * 按分区分
     */
    private PaymentProportionalAtr proportionalAtr;

    /**
     * 支給項目設定.課税区分
     */
    private Optional<TaxAtr> taxAtr;

    /**
     * 通勤区分
     */
    private Optional<WorkingAtr> workingAtr;

    private Optional<ItemRangeSetExportData> itemRangeSet;

    /**
     * 計算式コード
     */
    private String calcFomulaCd;

    /**
     * 計算式名
     */
    private String calcFomulaName;

    /**
     * 個人金額コード
     */
    private String personAmountCd;

    /**
     * 略名
     */
    private String personAmountName;

    /**
     * 共通金額
     */
    private String commonAmount;

    /**
     * 賃金テーブルコード
     */
    private String wageTblCode;

    /**
     * 賃金テーブル名
     */
    private String wageTblName;


}
