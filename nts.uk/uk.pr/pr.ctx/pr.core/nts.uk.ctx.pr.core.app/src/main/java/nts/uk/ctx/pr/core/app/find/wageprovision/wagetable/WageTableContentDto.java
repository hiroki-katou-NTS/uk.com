package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;

/**
 * 賃金テーブル内容
 */
@Data
@NoArgsConstructor
public class WageTableContentDto {

	/**
	 * 履歴ID
	 */
	private String historyID;

	/**
	 * 支払金額
	 */
	private List<ElementsCombinationPaymentAmountDto> payments;

	/**
	 * 資格グループ設定
	 */
	private List<QualificationGroupSettingContentDto> qualificationGroupSettings;

	public static WageTableContentDto fromDomainToDto(WageTableContent domain) {
		WageTableContentDto dto = new WageTableContentDto();
		dto.historyID = domain.getHistoryID();
		dto.payments = domain.getPayments().stream().map(ElementsCombinationPaymentAmountDto::fromDomainToDto)
				.collect(Collectors.toList());
		dto.qualificationGroupSettings = domain.getQualificationGroupSettings().orElse(Collections.emptyList()).stream()
				.map(QualificationGroupSettingContentDto::fromDomainToDto).collect(Collectors.toList());
		return dto;
	}
}
