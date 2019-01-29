package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;

/**
 * 要素項目
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementItemDto {
	// Merge domain 要素項目, 要素項目（マスタ）, 要素項目（数値）
	/**
	 * 要素項目（マスタ）.マスタコード
	 */
	private String masterCode;
	
	private String masterName;

	/**
	 * 要素項目(数値).枠番
	 */
	private Long frameNumber;

	/**
	 * .要素項目(数値)当該枠下限
	 */
	private BigDecimal frameLowerLimit;

	/**
	 * 要素項目(数値).当該枠上限
	 */
	private BigDecimal frameUpperLimit;
	
	private Long paymentAmount;

	public static ElementItemDto fromDomainToDto(ElementItem domain) {
		ElementItemDto dto = new ElementItemDto();
		dto.masterCode = domain.getMasterElementItem().map(i -> i.getMasterCode()).orElse(null);
		domain.getNumericElementItem().ifPresent(numericElementItem -> {
			dto.frameNumber = numericElementItem.getFrameNumber();
			dto.frameLowerLimit = numericElementItem.getFrameLowerLimit();
			dto.frameUpperLimit = numericElementItem.getFrameUpperLimit();
		});
		return dto;
	}

}
