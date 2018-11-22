package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementAttribute;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.MasterNumericInformation;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ElementAttributeDto{

    /**
     * マスタ数値区分
     */
    private Integer masterNumericInformation;

    /**
     * 固定の要素
     */
    private Integer fixedElement;

    /**
     * 任意追加の要素
     */
    private String optionalAdditionalElement;

    public static ElementAttributeDto fromDomainToDto(ElementAttribute domain) {
        ElementAttributeDto dto = new ElementAttributeDto();
        dto.masterNumericInformation = domain.getMasterNumericInformation().map(i -> i.value).orElse(null);
        dto.fixedElement = domain.getFixedElement().map(i -> i.value).orElse(null);
        dto.optionalAdditionalElement = domain.getOptionalAdditionalElement().map(PrimitiveValueBase::v).orElse(null);
        return dto;
    }

}
