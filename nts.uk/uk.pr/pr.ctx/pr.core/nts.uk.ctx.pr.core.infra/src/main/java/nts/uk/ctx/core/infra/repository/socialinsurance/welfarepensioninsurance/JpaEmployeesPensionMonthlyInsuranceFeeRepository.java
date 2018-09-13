package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.*;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtBonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtEmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtGradeWelfarePensionInsurancePremium;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaEmployeesPensionMonthlyInsuranceFeeRepository extends JpaRepository implements EmployeesPensionMonthlyInsuranceFeeRepository {

    private static final String GET_GRADE_WELFARE_PENSION_INSURANCE_PREMIUM_BY_HISTORY_ID = "SELECT a from QpbmtGradeWelfarePensionInsurancePremium a WHERE a.gradeWelfarePremiPk.historyId =:historyId";

    @Override
    public Optional<EmployeesPensionMonthlyInsuranceFee> getEmployeesPensionMonthlyInsuranceFeeByHistoryId(String historyId) {
        Optional<QpbmtEmployeesPensionMonthlyInsuranceFee> entity = this.queryProxy().find(historyId, QpbmtEmployeesPensionMonthlyInsuranceFee.class);
        if (!entity.isPresent())
            return Optional.empty();
        List<QpbmtGradeWelfarePensionInsurancePremium> details = this.queryProxy()
                .query(GET_GRADE_WELFARE_PENSION_INSURANCE_PREMIUM_BY_HISTORY_ID, QpbmtGradeWelfarePensionInsurancePremium.class)
                .setParameter("historyId", historyId)
                .getList();
        return Optional.of(toDomain(entity.get(), details));
    }

    /**
     * Return domain 厚生年金月額保険料額
     *
     * @param entity  QpbmtEmployeesPensionMonthlyInsuranceFee
     * @param details QpbmtGradeWelfarePensionInsurancePremium
     * @return EmployeesPensionMonthlyInsuranceFee
     */
    private EmployeesPensionMonthlyInsuranceFee toDomain(QpbmtEmployeesPensionMonthlyInsuranceFee entity, List<QpbmtGradeWelfarePensionInsurancePremium> details) {
        EmployeesPensionContributionRate maleContributionRate = new EmployeesPensionContributionRate(entity.maleIndividualBurdenRatio, entity.maleEmployeeContributionRatio, entity.maleIndividualExemptionRate, entity.maleEmployerExemptionRate);
        EmployeesPensionContributionRate femaleContributionRate = new EmployeesPensionContributionRate(entity.femaleIndividualBurdenRatio, entity.femaleEmployeeContributionRatio, entity.femaleIndividualExemptionRate, entity.femaleEmployerExemptionRate);
        EmployeesPensionClassification fractionClassification = new EmployeesPensionClassification(entity.personalFraction, entity.businessOwnerFraction);
        SalaryEmployeesPensionInsuranceRate salaryEmployeesPensionInsuranceRate = new SalaryEmployeesPensionInsuranceRate(entity.employeeShareAmountMethod, maleContributionRate, femaleContributionRate, fractionClassification);
        return new EmployeesPensionMonthlyInsuranceFee(
                entity.historyId,
                entity.autoCalculationCls,
                details.stream().map(x -> {
                    ContributionFee insuredBurden = new ContributionFee(x.insFemaleInsurancePremium, x.insMaleInsurancePremium, x.insFemaleExemptionInsurancePremium, x.insMaleExemptionInsurancePremium);
                    ContributionFee employeeBurden = new ContributionFee(x.empFemaleInsurancePremium, x.empMaleInsurancePremium, x.empFemaleExemptionInsurancePremium, x.empMaleExemptionInsurancePremium);
                    return new GradeWelfarePensionInsurancePremium(x.gradeWelfarePremiPk.employeePensionGrade, insuredBurden, employeeBurden);
                }).collect(Collectors.toList()),
                salaryEmployeesPensionInsuranceRate
        );
    }

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtEmployeesPensionMonthlyInsuranceFee.class, historyIds);
	}
}