package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 健康保険料率履歴
 */
@AllArgsConstructor
@Getter
public class HealthInsuranceFeeRateHistoryDto {

    /**
     * 社会保険事業所コード
     */
    private String socialInsuranceCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItemDto> history;

    /**
     * 健康保険料率履歴
     *
     * @param optDomain Optional<HealthInsuranceFeeRateHistory>
     * @return HealthInsuranceFeeRateHistoryDto
     */
    public static HealthInsuranceFeeRateHistoryDto fromDomain(String officeCode, Optional<HealthInsuranceFeeRateHistory> optDomain) {
        if (!optDomain.isPresent()) return new HealthInsuranceFeeRateHistoryDto(officeCode, Collections.EMPTY_LIST);
        val domain = optDomain.get();
        return new HealthInsuranceFeeRateHistoryDto(domain.getSocialInsuranceOfficeCode().v(),
                domain.getHistory().stream().map(YearMonthHistoryItemDto::fromDomainToDto).collect(Collectors.toList()));
    }
}