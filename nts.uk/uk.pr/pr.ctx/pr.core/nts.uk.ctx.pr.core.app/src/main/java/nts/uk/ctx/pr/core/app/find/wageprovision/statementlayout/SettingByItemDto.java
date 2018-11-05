package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;

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

    public SettingByItemDto(SettingByItem domain) {
        this.itemPosition = domain.getItemPosition();
        this.itemId = domain.getItemId();
    }
}
