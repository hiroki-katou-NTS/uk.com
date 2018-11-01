package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;

/**
 * 要素情報
 */
@Getter
@Setter
@NoArgsConstructor
public class ElementInformationDto extends DomainObject {

    /**
     * 一次元要素
     */
    private ElementAttributeDto oneDimensionalElement;

    /**
     * 二次元要素
     */
    private ElementAttributeDto threeDimensionalElement;

    /**
     * 三次元要素
     */
    private ElementAttributeDto twoDimensionalElement;

    public static ElementInformationDto fromDomain(ElementInformation domain) {
        ElementInformationDto dto = new ElementInformationDto();
        dto.setOneDimensionalElement(ElementAttributeDto.fromDomain(domain.getOneDimensionalElement()));
        dto.setThreeDimensionalElement(domain.getThreeDimensionalElement().map(ElementAttributeDto::fromDomain)
                .orElse(null));
        dto.setTwoDimensionalElement(domain.getTwoDimensionalElement().map(ElementAttributeDto::fromDomain)
                .orElse(null));
        return dto;
    }
}
