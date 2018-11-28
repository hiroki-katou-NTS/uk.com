package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementAttribute;

@Getter
@Setter
@NoArgsConstructor
public class ElementAttributeDto extends DomainObject {
    /**
     * 一次元要素
     */
    private Integer fixedElement;

    /**
     * 二次元要素
     */
    private String optionalAdditionalElement;

    /**
     * 三次元要素
     */
    private Integer masterNumericClassification;


    public static ElementAttributeDto fromDomain(ElementAttribute domain) {
        ElementAttributeDto dto = new ElementAttributeDto();
        dto.setFixedElement(domain.getFixedElement().map(x -> x.value).orElse(null));
        dto.setOptionalAdditionalElement(domain.getOptionalAdditionalElement().map(x -> x.v()).orElse(null));
        dto.setMasterNumericClassification(domain.getMasterNumericClassification().map(x -> x.value).orElse(null));
        return dto;
    }
}
