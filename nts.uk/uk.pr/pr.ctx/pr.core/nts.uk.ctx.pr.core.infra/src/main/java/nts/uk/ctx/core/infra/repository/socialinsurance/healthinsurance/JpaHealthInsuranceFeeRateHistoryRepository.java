package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceFeeRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 健康保険料率履歴
 */
@Stateless
public class JpaHealthInsuranceFeeRateHistoryRepository extends JpaRepository implements HealthInsuranceFeeRateHistoryRepository {

    private static final String GET_HEALTH_INSURANCE_FEE_RATE_HISTORY_BY_CID = "SELECT a FROM QpbmtHealthInsuranceFeeRateHistory a WHERE a.healthInsFeeHistPk.cid =:companyId";
    private static final String WHERE_OFFICE_CODE = " AND a.healthInsFeeHistPk.socialInsuranceOfficeCd =:officeCode ORDER BY a.startYearMonth DESC";
    private static final String STRING_EMPTY = "";
    private static final String DELETE = "DELETE FROM QpbmtHealthInsuranceFeeRateHistory a WHERE a.healthInsFeeHistPk.cid =:companyId"
    		+ " AND a.healthInsFeeHistPk.socialInsuranceOfficeCd =:officeCode";
    
    
    @Override
    public Optional<HealthInsuranceFeeRateHistory> getHealthInsuranceFeeRateHistoryByCid(String companyId, String officeCode) {
        val entity = this.queryProxy()
                .query(GET_HEALTH_INSURANCE_FEE_RATE_HISTORY_BY_CID + (!Objects.isNull(officeCode) ? WHERE_OFFICE_CODE : STRING_EMPTY), QpbmtHealthInsuranceFeeRateHistory.class)
                .setParameter("companyId", companyId)
                .setParameter("officeCode", officeCode).getList();
        return Optional.ofNullable(toDomain(entity));
    }

    /**
     * Entity to domain
     *
     * @param entity QpbmtHealthInsuranceFeeRateHistory
     * @return HealthInsuranceFeeRateHistory
     */
    private HealthInsuranceFeeRateHistory toDomain(List<QpbmtHealthInsuranceFeeRateHistory> entity) {
        if (entity.isEmpty()) return null;
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
    private QpbmtHealthInsuranceFeeRateHistory toEntity(String socialInsuranceOfficeCd, String historyId, Integer startYearMonth, Integer endYearMonth) {
        return new QpbmtHealthInsuranceFeeRateHistory(new QpbmtHealthInsuranceFeeRateHistoryPk(AppContexts.user().companyId(), socialInsuranceOfficeCd, historyId),
                startYearMonth, endYearMonth);
    }

	@Override
	public void deleteByCidAndCode(String companyId, String officeCode) {
		this.getEntityManager().createQuery(DELETE, QpbmtHealthInsuranceFeeRateHistory.class)
		.setParameter("companyId", companyId)
		.setParameter("officeCode", officeCode)
		.executeUpdate();
	}

	@Override
	public void add(HealthInsuranceFeeRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().insert(this.toEntity(domain.getSocialInsuranceOfficeCode().v(), item.identifier(), item.start().v(), item.end().v()));
		});
		
	}

	@Override
	public void update(HealthInsuranceFeeRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().update(this.toEntity(domain.getSocialInsuranceOfficeCode().v(), item.identifier(), item.start().v(), item.end().v()));
		});
	}

	@Override
	public void remove(HealthInsuranceFeeRateHistory domain) {
		this.deleteByCidAndCode(AppContexts.user().companyId(), domain.getSocialInsuranceOfficeCode().v());
		domain.getHistory().forEach(item -> {
			this.commandProxy().insert(this.toEntity(domain.getSocialInsuranceOfficeCode().v(), item.identifier(), item.start().v(), item.end().v()));
		});
	}
}
