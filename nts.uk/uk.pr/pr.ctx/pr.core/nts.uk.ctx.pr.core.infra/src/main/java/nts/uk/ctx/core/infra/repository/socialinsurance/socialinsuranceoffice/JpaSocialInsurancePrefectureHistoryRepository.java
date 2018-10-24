package nts.uk.ctx.core.infra.repository.socialinsurance.socialinsuranceoffice;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureHistory;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.socialinsuranceoffice.QpbmtSocialInsurancePrefectureHistory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;


@Stateless
public class JpaSocialInsurancePrefectureHistoryRepository extends JpaRepository implements SocialInsurancePrefectureHistoryRepository {
    
	private static final String FINDALL = "SELECT sh FROM QpbmtSocialInsurancePrefectureHistory sh";
	/**
     * Entity to domain
     *
     * @param entity QpbmtContributionRateHistory
     * @return ContributionRateHistory
     */
    private SocialInsurancePrefectureHistory toDomain(List<QpbmtSocialInsurancePrefectureHistory> entity) {
        List<YearMonthHistoryItem> history = entity.stream().map(item -> new YearMonthHistoryItem(
                item.historyId,
                new YearMonthPeriod(
                        new YearMonth(item.startYm),
                        new YearMonth(item.endYm)))).collect(Collectors.toList());
        return new SocialInsurancePrefectureHistory(history);
    }

    /**
     * Domain to entity
     *
     * @param historyId      履歴ID
     * @param startYearMonth 年月開始
     * @param endYearMonth   年月終了
     * @return QpbmtContributionRateHistory
     */
    private QpbmtSocialInsurancePrefectureHistory toEntity(String historyId, int startYearMonth, int endYearMonth) {
        return new QpbmtSocialInsurancePrefectureHistory(historyId, startYearMonth, endYearMonth);
    }

	@Override
	public SocialInsurancePrefectureHistory findAll() {
		return toDomain(this.queryProxy().query(FINDALL, QpbmtSocialInsurancePrefectureHistory.class)
       		 .getList());
	}
}
