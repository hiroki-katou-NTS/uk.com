package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
/**
 * 厚生年金保険料率履歴
 */
@AllArgsConstructor
public class WelfarePensionInsuranceRateHistoryDto {

    /**
     * 社会保険事業所コード
     */
    private String socialInsuranceOfficeCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItemDto> history;
    
    private String socialInsuranceOfficeName;

    /**
     * 厚生年金保険料率履歴
     *
     * @param cid                     会社ID
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param history                 履歴
     */
    public static WelfarePensionInsuranceRateHistoryDto fromDomainToDto(Optional<WelfarePensionInsuranceRateHistory> optDomain, String socialInsuranceOfficeName) {
    	if (!optDomain.isPresent()) return null;
    	WelfarePensionInsuranceRateHistory domain = optDomain.get();
        return new WelfarePensionInsuranceRateHistoryDto(domain.getSocialInsuranceOfficeCode().v(), domain.getHistory().stream().map(historyItem -> {
        	return YearMonthHistoryItemDto.fromDomainToDto(historyItem);
        }).collect(Collectors.toList()), socialInsuranceOfficeName);
    }
}
