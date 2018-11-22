package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;

@AllArgsConstructor
@Value
public class SettingByItemDto {
    /**
     * 項目位置
     */
    private int itemPosition;

    /**
     * 項目ID
     */
    private String itemId;

    String shortName;
    private PaymentItemDetailDto paymentItemDetailSet;
    private DeductionItemDetailDto deductionItemDetailSet;

    public SettingByItemDto(SettingByItem domain) {
        this.itemPosition = domain.getItemPosition();
        this.itemId = domain.getItemId();

        if(domain instanceof SettingByItemCustom) {
            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) domain;
            this.shortName = settingByItemCustom.getShortName();
            this.paymentItemDetailSet = settingByItemCustom.getPaymentItemDetailSet().map(i -> new PaymentItemDetailDto(i)).orElse(null);
            this.deductionItemDetailSet = settingByItemCustom.getDeductionItemDetailSet().map(i -> new DeductionItemDetailDto(i)).orElse(null);
        } else {
            this.shortName = null;
            this.paymentItemDetailSet = null;
            this.deductionItemDetailSet = null;
        }
    }
}
