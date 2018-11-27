package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementAttribute;

@Data
@NoArgsConstructor
public class ElementAttributeDto {

	/**
	 * マスタ数値区分
	 */
	private Integer masterNumericClassification;

	/**
	 * 固定の要素
	 */
	private String fixedElement;

	/**
	 * 任意追加の要素
	 */
	private String optionalAdditionalElement;
	
	private String displayName;

	public static ElementAttributeDto fromDomainToDto(ElementAttribute domain) {
		ElementAttributeDto dto = new ElementAttributeDto();
		dto.masterNumericClassification = domain.getMasterNumericAtr().map(i -> i.value).orElse(null);
		dto.fixedElement = domain.getFixedElement().map(i -> i.value).orElse(null);
		dto.optionalAdditionalElement = domain.getOptionalAdditionalElement().map(PrimitiveValueBase::v).orElse(null);
		dto.displayName = null;
		return dto;
	}

}
