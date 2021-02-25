package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValue;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationSetting;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalItemAppSetDto {
    private String code;
    private String name;
    private int useAtr;
    private String note;
    private List<OptItemSetDto> settingItems;

    public static OptionalItemAppSetDto fromDomain(OptionalItemApplicationSetting domain) {
        return new OptionalItemAppSetDto(
                domain.getCode().v(),
                domain.getName().v(),
                BooleanUtils.toInteger(domain.isUseAtr()),
                domain.getDescription().map(PrimitiveValue::v).orElse(null),
                domain.getSettingItems().stream().map(i -> new OptItemSetDto(i.getOptionalItemNo().v(), i.getDisplayOrder())).collect(Collectors.toList())
        );
    }
}
