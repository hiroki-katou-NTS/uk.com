package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaWelfarePensionInsuranceClassificationRepository extends JpaRepository
		implements WelfarePensionInsuranceClassificationRepository {

	private static final String FIND_HISTORY_BY_OFFICE_CODE = "SELECT a FROM QpbmtWelfarePensionInsuranceClassification WHERE a.welfarePenClsPk.cid = :cid AND a.welfarePenClsPk.officeCode = :officeCode";
    private static final String DELETE_BY_HISTORY_IDS = "DELETE FROM QpbmtWelfarePensionInsuranceClassification WHERE a.welfarePenClsPk.historyId IN :historyId";

	@Override
	public Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionHistoryByOfficeCode(String officeCode) {
		return this.getWelfarePensionHistory(this.findWelfarePensionClassficationByOfficeCode(officeCode));
	}

	@Override
	public Optional<WelfarePensionInsuranceClassification> getWelfarePensionInsuranceClassificationById( String historyId) {
		return this.queryProxy().find(historyId, QpbmtWelfarePensionInsuranceClassification.class).map(QpbmtWelfarePensionInsuranceClassification::toDomain);
	}

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
        if (!historyIds.isEmpty()) this.getEntityManager().createQuery(DELETE_BY_HISTORY_IDS, QpbmtWelfarePensionInsuranceClassification.class).setParameter("historyId", historyIds).executeUpdate();
	}
	
	@Override
	public void add(WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().insert(QpbmtWelfarePensionInsuranceClassification.toEntity(domain, officeCode, yearMonth));
	}
	
	@Override
	public void update(WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().update(QpbmtWelfarePensionInsuranceClassification.toEntity(domain, officeCode, yearMonth));
	}
	
	@Override
	public void remove(WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().remove(QpbmtWelfarePensionInsuranceClassification.toEntity(domain, officeCode, yearMonth));
	}

	@Override
	public void updatePreviousHistory(String officeCode, YearMonthHistoryItem yearMonth) {

	}

	private List<QpbmtWelfarePensionInsuranceClassification> findWelfarePensionClassficationByOfficeCode(String officeCode){
        return this.queryProxy().query(FIND_HISTORY_BY_OFFICE_CODE, QpbmtWelfarePensionInsuranceClassification.class).setParameter("cid", AppContexts.user().companyId()).setParameter("officeCode", officeCode).getList();
    }

    private Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionHistory (List<QpbmtWelfarePensionInsuranceClassification> entities) {
	    if (entities.isEmpty()) return Optional.empty();
	    return Optional.of(new WelfarePensionInsuranceRateHistory(entities.get(0).welfarePenClsPk.cid, entities.get(0).welfarePenClsPk.socialInsuranceOfficeCd, entities.stream().map(item -> new YearMonthHistoryItem(item.welfarePenClsPk.historyId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
    }


}
