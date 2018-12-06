package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;

/**
 * 要素範囲設定
 */
@Data
@NoArgsConstructor
public class ElementRangeSettingDto {

	/**
	 * 第一要素範囲
	 */
	private ElementRangeDto firstElementRange;

	/**
	 * 第二要素範囲
	 */
	private ElementRangeDto secondElementRange;

	/**
	 * 第三要素範囲
	 */
	private ElementRangeDto thirdElementRange;

	/**
	 * 履歴ID
	 */
	private String historyID;
	
	private String wageTableCode;

	public static ElementRangeSettingDto fromDomainToDto(ElementRangeSetting domain) {
		ElementRangeSettingDto dto = new ElementRangeSettingDto();
		dto.historyID = domain.getHistoryID();
		dto.firstElementRange = ElementRangeDto.fromDomainToDto(domain.getFirstElementRange());
		dto.secondElementRange = domain.getSecondElementRange().map(ElementRangeDto::fromDomainToDto).orElse(null);
		dto.thirdElementRange = domain.getThirdElementRange().map(ElementRangeDto::fromDomainToDto).orElse(null);
		return dto;
	}

}
