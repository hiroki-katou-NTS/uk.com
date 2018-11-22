package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;

/**
 * 要素情報
 */

@Data
@NoArgsConstructor
public class ElementInformationDto {

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
		dto.twoDimensionalElement = domain.getTwoDimensionalElement().map(ElementAttributeDto::fromDomainToDto)
				.orElse(null);
		dto.threeDimensionalElement = domain.getThreeDimensionalElement().map(ElementAttributeDto::fromDomainToDto)
				.orElse(null);
		return dto;
	}

}
