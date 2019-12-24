package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.DefaultAtr;

@Getter
@Setter
@NoArgsConstructor
public class  SettingByItemExportData {
    /**
     * 項目位置
     */
    private int itemPosition;

    /**
     * 項目ID
     */
    private String itemId;

    private String itemName;

    private PaymentExportData payment;

    private DeductionExportData deduction;

    private AttendExportData attend;

    private DefaultAtr defaultAtr;
}
