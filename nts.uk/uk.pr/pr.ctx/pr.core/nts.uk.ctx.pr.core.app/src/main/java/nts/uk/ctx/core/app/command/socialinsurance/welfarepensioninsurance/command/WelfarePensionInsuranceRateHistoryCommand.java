package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.shr.com.context.AppContexts;
/**
 * 厚生年金保険料率履歴
 */
@AllArgsConstructor
public class WelfarePensionInsuranceRateHistoryCommand {

    /**
     * 社会保険事業所コード
     */
    private String socialInsuranceOfficeCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItemCommand> history;

    /**
     * 厚生年金保険料率履歴
     *
     * @param cid                     会社ID
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param history                 履歴
     */
    public WelfarePensionInsuranceRateHistory fromCommandToDto() {
        return new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), this.socialInsuranceOfficeCode, this.history.stream().map(historyItem -> {
        	return historyItem.fromCommandToDomain();
        }).collect(Collectors.toList()));
    }
}
