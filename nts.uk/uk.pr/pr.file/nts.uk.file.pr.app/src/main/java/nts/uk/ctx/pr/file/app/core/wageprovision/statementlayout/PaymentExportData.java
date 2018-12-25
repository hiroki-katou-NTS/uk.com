package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentCaclMethodAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentProportionalAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentTotalObjAtr;

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
