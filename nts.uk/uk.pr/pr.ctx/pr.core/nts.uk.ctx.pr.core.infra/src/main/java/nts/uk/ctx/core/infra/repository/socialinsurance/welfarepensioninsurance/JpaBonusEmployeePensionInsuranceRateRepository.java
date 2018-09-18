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

@Stateless
public class JpaBonusEmployeePensionInsuranceRateRepository extends JpaRepository implements BonusEmployeePensionInsuranceRateRepository {

    @Override
    public Optional<BonusEmployeePensionInsuranceRate> getBonusEmployeePensionInsuranceRateById(String historyId) {
        return queryProxy().find(historyId, QpbmtBonusEmployeePensionInsuranceRate.class).map(this::toDomain);
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
                entity.historyId,
                entity.employeeShareAmountMethod,
                maleContributionRate,
                femaleContributionRate,
                fractionClassification);
    }

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtBonusEmployeePensionInsuranceRate.class, historyIds);
	}
	
	@Override
	public void add(BonusEmployeePensionInsuranceRate domain) {
		this.commandProxy().insert(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain));
	}
	
	@Override
	public void update(BonusEmployeePensionInsuranceRate domain) {
		this.commandProxy().update(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain));
	}
	
	@Override
	public void remove(BonusEmployeePensionInsuranceRate domain) {
		this.commandProxy().remove(QpbmtBonusEmployeePensionInsuranceRate.toEntity(domain));
	}
}
