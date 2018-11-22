package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementAttribute;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;

import java.util.Optional;

/**
* 要素情報
*/

@Data
@NoArgsConstructor
public class ElementInformationDto
{

    /**
    * 一次元要素
    */
    private ElementAttributeDto oneDimensionalElement;

    /**
    * 二次元要素
    */
    private ElementAttributeDto twoDimensionalElement;

    /**
    * 三次元要素
    */
    private ElementAttributeDto threeDimensionalElement;

    public static ElementInformationDto fromDomainToDto(ElementInformation domain) {
        ElementInformationDto dto = new ElementInformationDto();
        dto.oneDimensionalElement = ElementAttributeDto.fromDomainToDto(domain.getOneDimensionalElement());
        dto.twoDimensionalElement = domain.getTwoDimensionalElement().map(ElementAttributeDto::fromDomainToDto).orElse(null);
        dto.threeDimensionalElement = domain.getThreeDimensionalElement().map(ElementAttributeDto::fromDomainToDto).orElse(null);
        return dto;
    }
    
=======
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
>>>>>>> pj/pr/team_G/QMM019
}
