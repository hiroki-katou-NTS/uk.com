package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionCaclMethodAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionProportionalAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionTotalObjAtr;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class DeductionExportData {

    /**
     * 合計対象
     */
    private DeductionTotalObjAtr totalObj;

    /**
     * 計算方法
     */
    private DeductionCaclMethodAtr calcMethod;

    /**
     * 按分区分
     */
    private DeductionProportionalAtr proportionalAtr;

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

    /**
     * 支給相殺
     */
    private String supplyOffset;

    /**
     * 略名
     */
    private String supplyOffsetName;

}
