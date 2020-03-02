package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRange;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.NumericElementRange;

/**
 * 要素範囲
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementRangeDto {
	// Merge domain 要素範囲, 数値要素範囲
	/**
	 * 数値要素範囲.きざみ単位
	 */
	private BigDecimal stepIncrement;

	/**
	 * 数値要素範囲.範囲下限
	 */
	private BigDecimal rangeLowerLimit;

	/**
	 * 数値要素範囲.範囲上限
	 */
	private BigDecimal rangeUpperLimit;

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
