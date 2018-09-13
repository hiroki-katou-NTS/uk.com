package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceFeeRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 健康保険料率履歴
 */
@Stateless
public class JpaHealthInsuranceFeeRateHistoryRepository extends JpaRepository implements HealthInsuranceFeeRateHistoryRepository {

    private static final String GET_HEALTH_INSURANCE_FEE_RATE_HISTORY_BY_CID = "SELECT a FROM QpbmtHealthInsuranceFeeRateHistory a WHERE a.healthInsFeeHistPk.cid =:companyId";
    private static final String WHERE_OFFICE_CODE = " AND a.healthInsFeeHistPk.socialInsuranceOfficeCd =:officeCode";
    private static final String STRING_EMPTY = "";

    @Override
    public HealthInsuranceFeeRateHistory getHealthInsuranceFeeRateHistoryByCid(String companyId, String officeCode) {
        List<QpbmtHealthInsuranceFeeRateHistory> entity = this.queryProxy()
                .query(GET_HEALTH_INSURANCE_FEE_RATE_HISTORY_BY_CID + (!Objects.isNull(officeCode) ? WHERE_OFFICE_CODE : STRING_EMPTY), QpbmtHealthInsuranceFeeRateHistory.class)
                .setParameter("companyId", companyId)
                .setParameter("officeCode", officeCode).getList();
        return toDomain(entity);
    }

    /**
     * Entity to domain
     *
     * @param entity QpbmtHealthInsuranceFeeRateHistory
     * @return HealthInsuranceFeeRateHistory
     */
    private HealthInsuranceFeeRateHistory toDomain(List<QpbmtHealthInsuranceFeeRateHistory> entity) {
        String companyId = entity.get(0).healthInsFeeHistPk.cid;
        String socialInsuranceCode = entity.get(0).healthInsFeeHistPk.socialInsuranceOfficeCd;
        List<YearMonthHistoryItem> history = entity.stream().map(item -> new YearMonthHistoryItem(
                item.healthInsFeeHistPk.historyId,
                new YearMonthPeriod(
                        new YearMonth(item.startYearMonth),
                        new YearMonth(item.endYearMonth)))).collect(Collectors.toList());
        return new HealthInsuranceFeeRateHistory(companyId, socialInsuranceCode, history);
    }

    /**
     * Domain to entity
     *
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param historyId               履歴ID
     * @param startYearMonth          年月開始
     * @param endYearMonth            年月終了
     * @return QpbmtContributionRateHistory
     */
    private QpbmtHealthInsuranceFeeRateHistory toEntity(String socialInsuranceOfficeCd, String historyId, int startYearMonth, int endYearMonth) {
        return new QpbmtHealthInsuranceFeeRateHistory(new QpbmtHealthInsuranceFeeRateHistoryPk(AppContexts.user().companyId(), socialInsuranceOfficeCd, historyId),
                startYearMonth, endYearMonth);
    }
}
