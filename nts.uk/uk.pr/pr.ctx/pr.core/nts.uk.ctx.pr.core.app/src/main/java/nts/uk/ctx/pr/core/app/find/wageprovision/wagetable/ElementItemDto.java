package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;

import java.util.Optional;

/**
* 要素項目
*/

@Data
@NoArgsConstructor
public class ElementItemDto {
    // Merge domain 要素項目, 要素項目（マスタ）, 要素項目（数値）
    /**
     * 要素項目（マスタ）.マスタコード
     */
    private String masterCode;

    /**
     * 要素項目(数値).枠番
     */
    private Integer frameNumber;

    /**
     * .要素項目(数値)当該枠下限
     */
    private Integer frameLowerLimit;

    /**
     * 要素項目(数値).当該枠上限
     */
    private Integer frameUpperLimit;

    public static ElementItemDto fromDomainToDto(ElementItem domain) {
        ElementItemDto dto = new ElementItemDto();
        dto.masterCode = domain.getMasterElementItem().map(i -> i.getMasterCode().v()).orElse(null);
        domain.getNumericElementItem().ifPresent(numericElementItem -> {
            dto.frameNumber = numericElementItem.getFrameNumber().v();
            dto.frameLowerLimit = numericElementItem.getFrameLowerLimit().v();
            dto.frameUpperLimit = numericElementItem.getFrameUpperLimit().v();
        });
        return dto;
    }
    
}
