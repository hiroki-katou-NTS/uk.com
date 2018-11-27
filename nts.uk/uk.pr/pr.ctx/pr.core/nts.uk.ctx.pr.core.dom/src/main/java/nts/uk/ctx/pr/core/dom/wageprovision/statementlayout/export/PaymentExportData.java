package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentCaclMethodAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentProportionalAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentTotalObjAtr;

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
}
