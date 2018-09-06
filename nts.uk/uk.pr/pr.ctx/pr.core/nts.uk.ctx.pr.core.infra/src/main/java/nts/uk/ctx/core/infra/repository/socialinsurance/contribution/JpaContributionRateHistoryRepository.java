package nts.uk.ctx.core.infra.repository.socialinsurance.contribution;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.contribution.QpbmtContributionRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaContributionRateHistoryRepository extends JpaRepository implements ContributionRateHistoryRepository {

    /**
     * Convert entity to domain
     *
     * @param entity QpbmtContributionRateHistory
     * @return ContributionRateHistory
     */
    private ContributionRateHistory toDomain(List<QpbmtContributionRateHistory> entity) {
        String companyId = entity.get(0).contributionHistPk.cid;
        String socialInsuranceCode = entity.get(0).contributionHistPk.socialInsuranceOfficeCd;
        List<YearMonthHistoryItem> history = entity.stream().map(item -> new YearMonthHistoryItem(
                item.contributionHistPk.historyId,
                new YearMonthPeriod(
                        new YearMonth(item.startYearMonth),
                        new YearMonth(item.endYearMonth)))).collect(Collectors.toList());
        return new ContributionRateHistory(companyId, socialInsuranceCode, history);
    }

    /**
     * Convert to Entity
     *
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param historyId               履歴ID
     * @param startYearMonth          年月開始
     * @param endYearMonth            年月終了
     * @return QpbmtContributionRateHistory
     */
    private QpbmtContributionRateHistory toEntity(String socialInsuranceOfficeCd, String historyId, int startYearMonth, int endYearMonth) {
        return new QpbmtContributionRateHistory(new QpbmtContributionRateHistoryPk(AppContexts.user().companyId(), socialInsuranceOfficeCd, historyId),
                startYearMonth, endYearMonth);
    }
}
