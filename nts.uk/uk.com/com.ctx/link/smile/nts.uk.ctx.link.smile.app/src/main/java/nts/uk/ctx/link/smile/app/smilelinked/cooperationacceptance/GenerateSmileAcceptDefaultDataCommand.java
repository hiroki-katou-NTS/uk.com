package nts.uk.ctx.link.smile.app.smilelinked.cooperationacceptance;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceItem;

@Value
public class GenerateSmileAcceptDefaultDataCommand {

    int acceptanceItem;

    String settingCode;

    public SmileCooperationAcceptanceItem getSmileCooperationAcceptanceItem() {
        return SmileCooperationAcceptanceItem.valueOf(acceptanceItem);
    }

    public ExternalImportCode getExternalImportCode() {
        return new ExternalImportCode(settingCode);
    }
}
