package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceRateHistoryPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaWelfarePensionInsuranceRateHistoryRepository extends JpaRepository implements WelfarePensionInsuranceRateHistoryRepository {
	private static final String FIND_ALL = "SELECT a FROM QpbmtWelfarePensionInsuranceRateHistory a WHERE a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid";
	private static final String DELETE = "DELETE FROM QpbmtWelfarePensionInsuranceRateHistory a WHERE a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid AND a.QpbmtWelfarePensionInsuranceRateHistoryPk.socialInsuranceOfficeCd =:officeCode";
	private static final String FIND_BY_OFFICE_CODE = "SELECT a FROM QpbmtWelfarePensionInsuranceRateHistory a WHERE a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid AND a.QpbmtWelfarePensionInsuranceRateHistoryPk.socialInsuranceOfficeCd =:officeCode AND a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid";
	private static final String DELETE_BY_OFFICE_CODE = "DELETE FROM QpbmtWelfarePensionInsuranceRateHistory a WHERE a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid AND a.QpbmtWelfarePensionInsuranceRateHistoryPk.socialInsuranceOfficeCd =:officeCode AND a.QpbmtWelfarePensionInsuranceRateHistoryPk.cid =: cid";
	
    /**
     * Entity to domain
     *
     * @param entity QpbmtWelfarePensionInsuranceRateHistory
     * @return ContributionRateHistory
     */
    private Optional<WelfarePensionInsuranceRateHistory> toDomain(List<QpbmtWelfarePensionInsuranceRateHistory> entity) {
        if (entity.isEmpty()) return Optional.empty();
    	String companyId = entity.get(0).welfarePenHistPk.cid;
        String socialInsuranceCode = entity.get(0).welfarePenHistPk.socialInsuranceOfficeCd;
        List<YearMonthHistoryItem> history = new ArrayList<>();
        entity.forEach(item -> {
        	history.add(new YearMonthHistoryItem(item.welfarePenHistPk.historyId,
                    new YearMonthPeriod(
                            new YearMonth(item.startYearMonth),
                            new YearMonth(item.endYearMonth))));
        });
        return Optional.of(new WelfarePensionInsuranceRateHistory(companyId, socialInsuranceCode, history));
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


	@Override
	public Optional<WelfarePensionInsuranceRateHistory> getWelfarePensionInsuranceRateHistoryByOfficeCode(String officeCode) {
		return this.toDomain(this.queryProxy().query(FIND_BY_OFFICE_CODE, QpbmtWelfarePensionInsuranceRateHistory.class).setParameter("cid", AppContexts.user().companyId()).setParameter("officeCode", officeCode).setParameter("cid", AppContexts.user().companyId()).getList());
	}


	@Override
	public List<WelfarePensionInsuranceRateHistory> findAll() {
		//TODO
		return Collections.EMPTY_LIST;
	}


	@Override
	public void deleteByCidAndCode(String cid, String officeCode) {
		this.getEntityManager().createQuery(DELETE, QpbmtWelfarePensionInsuranceRateHistory.class)
		.setParameter("companyID", cid)
		.setParameter("officeCode", officeCode)
		.executeUpdate();
	}

	
	@Override
	public void remove(WelfarePensionInsuranceRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().remove(this.toEntity(domain.getSocialInsuranceOfficeCode().v(), item.identifier(), item.start().v(), item.end().v()));
		});
	}


	@Override
	public void add(WelfarePensionInsuranceRateHistory domain) {
		domain.getHistory().forEach(item -> {
			this.commandProxy().insert(this.toEntity(domain.getSocialInsuranceOfficeCode().v(), item.identifier(), item.start().v(), item.end().v()));
		});
	}
}
