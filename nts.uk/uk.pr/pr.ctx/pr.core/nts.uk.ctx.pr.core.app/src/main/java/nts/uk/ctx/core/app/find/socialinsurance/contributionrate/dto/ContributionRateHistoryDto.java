package nts.uk.ctx.core.app.find.socialinsurance.contributionrate.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistory;

/**
 * 拠出金率履歴
 */
@AllArgsConstructor
@Data
public class ContributionRateHistoryDto {

	/**
	 * 社会保険事業所コード
	 */
	private String socialInsuranceCode;

	/**
	 * 履歴
	 */
	private List<YearMonthHistoryItemDto> history;

	/**
	 * 拠出金率履歴
	 *
	 * @param companyId
	 *            会社ID
	 * @param socialInsuranceCode
	 *            社会保険事業所コード
	 * @param history
	 *            履歴
	 */
	public static ContributionRateHistoryDto fromDomainToDto(Optional<ContributionRateHistory> optDomain,
			String socialInsuranceCode) {
		if (!optDomain.isPresent()) {
			return new ContributionRateHistoryDto(socialInsuranceCode, Collections.EMPTY_LIST);
		}
		ContributionRateHistory domain = optDomain.get();
		return new ContributionRateHistoryDto(socialInsuranceCode, domain.getHistory().stream().map(historyItem -> {
			return YearMonthHistoryItemDto.fromDomainToDto(historyItem);
		}).collect(Collectors.toList()));
	}

}
