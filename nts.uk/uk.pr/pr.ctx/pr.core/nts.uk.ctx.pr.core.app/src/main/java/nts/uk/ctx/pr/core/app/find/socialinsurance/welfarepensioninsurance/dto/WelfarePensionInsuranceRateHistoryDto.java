package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
/**
 * 厚生年金保険料率履歴
 */
@AllArgsConstructor
@Data
public class WelfarePensionInsuranceRateHistoryDto {

    /**
     * 社会保険事業所コード
     */
    private String socialInsuranceCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItemDto> history;

    /**
     * 厚生年金保険料率履歴
     *
     * @param cid                     会社ID
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param history                 履歴
     */
    public static WelfarePensionInsuranceRateHistoryDto fromDomainToDto(Optional<WelfarePensionInsuranceRateHistory> optDomain, String socialInsuranceOfficeCode) {
    	if (!optDomain.isPresent()){
    		return new WelfarePensionInsuranceRateHistoryDto(socialInsuranceOfficeCode, Collections.EMPTY_LIST);
    	}
    	WelfarePensionInsuranceRateHistory domain = optDomain.get();
        return new WelfarePensionInsuranceRateHistoryDto(socialInsuranceOfficeCode, domain.getHistory().stream().map(historyItem -> {
        	return YearMonthHistoryItemDto.fromDomainToDto(historyItem);
        }).collect(Collectors.toList()));
    }
}
