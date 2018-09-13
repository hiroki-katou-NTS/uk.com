package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
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
    	List<YearMonthHistoryItem> history = new ArrayList<>();
    	this.history.forEach(item -> {
        	history.add(new YearMonthHistoryItem(item.historyId,
                    new YearMonthPeriod(
                            new YearMonth(item.startMonth),
                            new YearMonth(item.endMonth))));
        });
        return new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), this.socialInsuranceOfficeCode, history);
    }
}
