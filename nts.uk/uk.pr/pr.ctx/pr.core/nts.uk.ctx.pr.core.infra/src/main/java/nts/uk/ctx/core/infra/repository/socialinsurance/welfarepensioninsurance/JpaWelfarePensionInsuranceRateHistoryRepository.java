package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaWelfarePensionInsuranceRateHistoryRepository extends JpaRepository implements WelfarePensionInsuranceRateHistoryRepository {

    /**
     * Entity to domain
     *
     * @param entity QpbmtWelfarePensionInsuranceRateHistory
     * @return ContributionRateHistory
     */
    private WelfarePensionInsuranceRateHistory toDomain(List<QpbmtWelfarePensionInsuranceRateHistory> entity) {
        String companyId = entity.get(0).welfarePenHistPk.cid;
        String socialInsuranceCode = entity.get(0).welfarePenHistPk.socialInsuranceOfficeCd;
        List<YearMonthHistoryItem> history = entity.stream().map(item -> new YearMonthHistoryItem(
                item.welfarePenHistPk.historyId,
                new YearMonthPeriod(
                        new YearMonth(item.startYearMonth),
                        new YearMonth(item.endYearMonth)))).collect(Collectors.toList());
        return new WelfarePensionInsuranceRateHistory(companyId, socialInsuranceCode, history);
    }


    /**
     * Domain to entity
     *
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param historyId               履歴ID
     * @param startYearMonth          年月開始
     * @param endYearMonth            年月終了
     * @return QpbmtWelfarePensionInsuranceRateHistory
     */
    private QpbmtWelfarePensionInsuranceRateHistory toEntity(String socialInsuranceOfficeCd, String historyId, int startYearMonth, int endYearMonth) {
        return new QpbmtWelfarePensionInsuranceRateHistory(new QpbmtWelfarePensionInsuranceRateHistoryPk(AppContexts.user().companyId(), socialInsuranceOfficeCd, historyId),
                startYearMonth, endYearMonth);
    }
}
