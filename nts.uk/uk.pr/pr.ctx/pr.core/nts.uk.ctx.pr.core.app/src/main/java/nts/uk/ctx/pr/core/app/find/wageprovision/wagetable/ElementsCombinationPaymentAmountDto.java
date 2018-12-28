package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;

/**
 * 要素の組み合わせで支払う金額
 */
@Data
@NoArgsConstructor
public class ElementsCombinationPaymentAmountDto {

	/**
	 * 賃金テーブル支給金額
	 */
	private Long wageTablePaymentAmount;

	/**
	 * 要素属性
	 */
	private ContentElementAttributeDto elementAttribute;

	public static ElementsCombinationPaymentAmountDto fromDomainToDto(ElementsCombinationPaymentAmount domain) {
		ElementsCombinationPaymentAmountDto dto = new ElementsCombinationPaymentAmountDto();
		dto.wageTablePaymentAmount = domain.getWageTablePaymentAmount().v();
		dto.elementAttribute = ContentElementAttributeDto.fromDomainToDto(domain.getElementAttribute());
		return dto;
	}

}
