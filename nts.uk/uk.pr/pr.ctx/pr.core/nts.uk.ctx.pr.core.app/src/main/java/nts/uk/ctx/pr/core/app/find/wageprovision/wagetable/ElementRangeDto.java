package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRange;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.NumericElementRange;

import java.util.Optional;

/**
 * 要素範囲
 */

@NoArgsConstructor
@Data
public class ElementRangeDto{
    // Merge domain 要素範囲, 数値要素範囲
    /**
     * 数値要素範囲.きざみ単位
     */
    private Integer stepIncrement;

    /**
     * 数値要素範囲.範囲下限
     */
    private Integer rangeLowerLimit;

    /**
     * 数値要素範囲.範囲上限
     */
    private Integer rangeUpperLimit;

    public static ElementRangeDto fromDomainToDto(ElementRange domain) {
        return domain.getNumericElementRange().map(ElementRangeDto::fromNumericElementToDto).orElse(null);
    }

    public static ElementRangeDto fromNumericElementToDto(NumericElementRange domain) {
        ElementRangeDto dto = new ElementRangeDto();
        dto.stepIncrement = domain.getStepIncrement().v();
        dto.rangeLowerLimit = domain.getRangeLowerLimit().v();
        dto.rangeUpperLimit = domain.getRangeUpperLimit().v();
        return dto;
    }
}
