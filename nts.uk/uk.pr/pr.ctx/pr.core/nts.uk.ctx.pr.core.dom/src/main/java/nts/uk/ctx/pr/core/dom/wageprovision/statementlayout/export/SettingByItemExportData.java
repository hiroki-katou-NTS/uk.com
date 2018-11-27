package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SettingByItemExportData {
    /**
     * 項目位置
     */
    private int itemPosition;

    /**
     * 項目ID
     */
    private String itemId;

    private String itemName;

    @Setter
    private PaymentExportData payment;

    @Setter
    private DeductionExportData deduction;

    @Setter
    private AttendExportData attend;

    public SettingByItemExportData(int itemPosition, String itemId, String itemName) {
        this.itemPosition = itemPosition;
        this.itemId = itemId;
        this.itemName = itemName;
    }
}
