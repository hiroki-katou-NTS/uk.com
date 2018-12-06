package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ContentElementAttribute;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;

import java.util.Optional;

/**
 * 要素属性
 */
@Data
@NoArgsConstructor
public class ContentElementAttributeDto{

    /**
     * 第一要素項目
     */
    private ElementItemDto firstElementItem;

    /**
     * 第二要素項目
     */
    private ElementItemDto secondElementItem;

    /**
     * 第三要素項目
     */
    private ElementItemDto thirdElementItem;

    public static ContentElementAttributeDto fromDomainToDto(ContentElementAttribute domain){
        ContentElementAttributeDto dto = new ContentElementAttributeDto();
        dto.firstElementItem = ElementItemDto.fromDomainToDto(domain.getFirstElementItem());
        dto.secondElementItem = domain.getSecondElementItem().map(ElementItemDto::fromDomainToDto).orElse(null);
        dto.thirdElementItem = domain.getThirdElementItem().map(ElementItemDto::fromDomainToDto).orElse(null);
        return dto;
    }


}
