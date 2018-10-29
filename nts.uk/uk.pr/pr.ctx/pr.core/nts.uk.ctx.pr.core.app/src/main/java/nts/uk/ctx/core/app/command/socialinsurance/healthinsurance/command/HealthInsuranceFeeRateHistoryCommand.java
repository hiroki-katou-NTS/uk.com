package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 健康保険料率履歴
 */
@AllArgsConstructor
@Getter
public class HealthInsuranceFeeRateHistoryCommand {

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
    public static HealthInsuranceFeeRateHistoryCommand fromDomain(Optional<HealthInsuranceFeeRateHistory> optDomain) {
        if (!optDomain.isPresent()) return null;
        val domain = optDomain.get();
        return new HealthInsuranceFeeRateHistoryCommand(domain.getSocialInsuranceOfficeCode().v(),
                domain.getHistory().stream().map(YearMonthHistoryItemDto::fromDomainToDto).collect(Collectors.toList()));
    }
}