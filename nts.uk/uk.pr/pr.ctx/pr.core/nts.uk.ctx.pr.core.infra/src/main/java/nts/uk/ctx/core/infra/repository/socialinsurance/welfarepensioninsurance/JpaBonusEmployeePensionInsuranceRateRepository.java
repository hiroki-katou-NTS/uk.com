package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionContributionRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtBonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtBonusEmployeePensionInsuranceRatePk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import sun.awt.AppContext;

@Stateless
public class JpaBonusEmployeePensionInsuranceRateRepository extends JpaRepository implements BonusEmployeePensionInsuranceRateRepository {

	private static final String FIND_BY_HISTORY_ID = "SELECT a FROM QpbmtBonusEmployeePensionInsuranceRate a WHERE a.welfarePenBonusPk.historyId =:historyId";
	private static final String DELETE_BY_HISTORY_ID = "DELETE FROM QpbmtBonusEmployeePensionInsuranceRate a WHERE a.welfarePenBonusPk.historyId IN :historyId";

    @Override
    public Optional<BonusEmployeePensionInsuranceRate> getBonusEmployeePensionInsuranceRateById(String historyId) {
        return this.queryProxy().query(FIND_BY_HISTORY_ID, QpbmtBonusEmployeePensionInsuranceRate.class).setParameter("historyId", historyId).getSingle().map(this::toDomain);
    }

    /**
     * Return domain 賞与厚生年金保険料率
     *
     * @param entity QpbmtBonusEmployeePensionInsuranceRate
     * @return BonusEmployeePensionInsuranceRate
     */
    private BonusEmployeePensionInsuranceRate toDomain(QpbmtBonusEmployeePensionInsuranceRate entity) {
        EmployeesPensionContributionRate maleContributionRate   = new EmployeesPensionContributionRate(entity.maleIndividualBurdenRatio, entity.maleEmployeeContributionRatio, entity.maleIndividualExemptionRate, entity.maleEmployerExemptionRate);
        EmployeesPensionContributionRate femaleContributionRate = new EmployeesPensionContributionRate(entity.femaleIndividualBurdenRatio, entity.femaleEmployeeContributionRatio, entity.femaleIndividualExemptionRate, entity.femaleEmployerExemptionRate);
        EmployeesPensionClassification fractionClassification   = new EmployeesPensionClassification(entity.personalFraction, entity.businessOwnerFraction);
        return new BonusEmployeePensionInsuranceRate(
                entity.welfarePenBonusPk.historyId,
                entity.employeeShareAmountMethod,
                maleContributionRate,
                femaleContributionRate,
                fractionClassification);
    }

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
    	this.getEntityManager().createQuery(DELETE_BY_HISTORY_ID, QpbmtBonusEmployeePensionInsuranceRate.class).setParameter("historyId", historyIds).executeUpdate();
	}
	
	@Override
	public void add(BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().insert(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain, officeCode, yearMonth));
	}
	
	@Override
	public void update(BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().update(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain, officeCode, yearMonth));
	}
	
	@Override
	public void remove(BonusEmployeePensionInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().remove(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain, officeCode, yearMonth));
	}

	@Override
	public void updateHistory(String officeCode, YearMonthHistoryItem yearMonth) {
        Optional<QpbmtBonusEmployeePensionInsuranceRate> opt_entity = this.queryProxy().find(new QpbmtBonusEmployeePensionInsuranceRatePk(AppContexts.user().companyId(), officeCode, yearMonth.identifier()), QpbmtBonusEmployeePensionInsuranceRate.class);
        if (!opt_entity.isPresent()) return;
        QpbmtBonusEmployeePensionInsuranceRate entity = opt_entity.get();
        entity.startYearMonth = yearMonth.start().v();
        entity.endYearMonth = yearMonth.end().v();
        this.commandProxy().update(entity);
	}
}
