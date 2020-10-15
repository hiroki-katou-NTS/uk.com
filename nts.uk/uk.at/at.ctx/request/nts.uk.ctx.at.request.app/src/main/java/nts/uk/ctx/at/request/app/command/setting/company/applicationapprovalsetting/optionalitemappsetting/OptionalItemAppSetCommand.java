package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.optionalitemappsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.ApplicationSettingItem;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationSetting;
import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemNo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionalItemAppSetCommand {
    private String code;
    private String name;
    private int useAtr;
    private String note;
    private List<OptItemSetCommand> settingItems;

    public OptionalItemApplicationSetting toDomain(String companyId) {
        return OptionalItemApplicationSetting.create(
                companyId,
                code,
                name,
                useAtr,
                note,
                settingItems.stream().map(i -> new ApplicationSettingItem(new OptionalItemNo(i.getNo()), i.getDispOrder())).collect(Collectors.toList())
        );
    }
}
