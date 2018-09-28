package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.ContributionFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.SalaryEmployeesPensionInsuranceRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtEmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtGradeWelfarePensionInsurancePremium;

@Stateless
public class JpaEmployeesPensionMonthlyInsuranceFeeRepository extends JpaRepository
		implements EmployeesPensionMonthlyInsuranceFeeRepository {

	private static final String GET_GRADE_WELFARE_PENSION_INSURANCE_PREMIUM_BY_HISTORY_ID = "SELECT a from QpbmtGradeWelfarePensionInsurancePremium a WHERE a.gradeWelfarePremiPk.historyId =:historyId";
	private static final String DELETE_GRADE_WELFARE_PENSION_INSURANCE_BY_HISTORY_ID = "DELETE from QpbmtGradeWelfarePensionInsurancePremium a WHERE a.gradeWelfarePremiPk.historyId IN :historyId";

	@Override
	public Optional<EmployeesPensionMonthlyInsuranceFee> getEmployeesPensionMonthlyInsuranceFeeByHistoryId(
			String historyId) {
		Optional<QpbmtEmployeesPensionMonthlyInsuranceFee> entity = this.queryProxy().find(historyId,
				QpbmtEmployeesPensionMonthlyInsuranceFee.class);
		if (!entity.isPresent())
			return Optional.empty();
		List<QpbmtGradeWelfarePensionInsurancePremium> details = this.queryProxy()
				.query(GET_GRADE_WELFARE_PENSION_INSURANCE_PREMIUM_BY_HISTORY_ID,
						QpbmtGradeWelfarePensionInsurancePremium.class)
				.setParameter("historyId", historyId).getList();
		return Optional.of(toDomain(entity.get(), details));
	}

	/**
	 * Return domain 厚生年金月額保険料額
	 *
	 * @param entity
	 *            QpbmtEmployeesPensionMonthlyInsuranceFee
	 * @param details
	 *            QpbmtGradeWelfarePensionInsurancePremium
	 * @return EmployeesPensionMonthlyInsuranceFee
	 */
	private EmployeesPensionMonthlyInsuranceFee toDomain(QpbmtEmployeesPensionMonthlyInsuranceFee entity,
			List<QpbmtGradeWelfarePensionInsurancePremium> details) {
		EmployeesPensionContributionRate maleContributionRate = new EmployeesPensionContributionRate(
				entity.maleIndividualBurdenRatio, entity.maleEmployeeContributionRatio,
				entity.maleIndividualExemptionRate, entity.maleEmployerExemptionRate);
		EmployeesPensionContributionRate femaleContributionRate = new EmployeesPensionContributionRate(
				entity.femaleIndividualBurdenRatio, entity.femaleEmployeeContributionRatio,
				entity.femaleIndividualExemptionRate, entity.femaleEmployerExemptionRate);
		EmployeesPensionClassification fractionClassification = new EmployeesPensionClassification(
				entity.personalFraction, entity.businessOwnerFraction);
		SalaryEmployeesPensionInsuranceRate salaryEmployeesPensionInsuranceRate = new SalaryEmployeesPensionInsuranceRate(
				entity.employeeShareAmountMethod, maleContributionRate, femaleContributionRate, fractionClassification);
		return new EmployeesPensionMonthlyInsuranceFee(entity.historyId, entity.autoCalculationCls,
				details.stream().map(x -> {
					ContributionFee insuredBurden = new ContributionFee(x.insFemaleInsurancePremium,
							x.insMaleInsurancePremium, x.insFemaleExemptionInsurancePremium,
							x.insMaleExemptionInsurancePremium);
					ContributionFee employeeBurden = new ContributionFee(x.empFemaleInsurancePremium,
							x.empMaleInsurancePremium, x.empFemaleExemptionInsurancePremium,
							x.empMaleExemptionInsurancePremium);
					return new GradeWelfarePensionInsurancePremium(x.gradeWelfarePremiPk.employeePensionGrade,
							insuredBurden, employeeBurden);
				}).collect(Collectors.toList()), salaryEmployeesPensionInsuranceRate);
	}

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtEmployeesPensionMonthlyInsuranceFee.class, historyIds);
		this.deleteGradeWelfareByHistoryIds(historyIds);
	}

	@Override
	public void add(EmployeesPensionMonthlyInsuranceFee domain) {
		this.commandProxy().insert(QpbmtEmployeesPensionMonthlyInsuranceFee.toEntity(domain));
		List<QpbmtGradeWelfarePensionInsurancePremium> listEntity = QpbmtGradeWelfarePensionInsurancePremium
				.toEntity(domain);
		this.commandProxy().insertAll(listEntity);
	}

	public void update(EmployeesPensionMonthlyInsuranceFee domain) {
		this.commandProxy().update(QpbmtEmployeesPensionMonthlyInsuranceFee.toEntity(domain));
		if (domain.getAutoCalculationCls() == AutoCalculationExecutionCls.AUTO) {
			this.deleteGradeWelfareByHistoryIds(Arrays.asList(domain.getHistoryId()));
			this.commandProxy().insertAll(QpbmtGradeWelfarePensionInsurancePremium.toEntity(domain));
		}
	}

	public void remove(EmployeesPensionMonthlyInsuranceFee domain) {
		this.commandProxy().remove(QpbmtEmployeesPensionMonthlyInsuranceFee.toEntity(domain));
		List<QpbmtGradeWelfarePensionInsurancePremium> listEntity = QpbmtGradeWelfarePensionInsurancePremium
				.toEntity(domain);
		this.commandProxy().removeAll(listEntity);
	}

	public void deleteGradeWelfareByHistoryIds(List<String> historyIds) {
		this.getEntityManager()
				.createQuery(DELETE_GRADE_WELFARE_PENSION_INSURANCE_BY_HISTORY_ID,
						QpbmtGradeWelfarePensionInsurancePremium.class)
				.setParameter("historyId", historyIds).executeUpdate();
	}

	@Override
	public void updateWelfarePension(EmployeesPensionMonthlyInsuranceFee data) {
		this.commandProxy().updateAll(QpbmtGradeWelfarePensionInsurancePremium.toEntity(data));
	}

	@Override
	public void insertWelfarePension(EmployeesPensionMonthlyInsuranceFee data) {
		this.commandProxy().insertAll(QpbmtGradeWelfarePensionInsurancePremium.toEntity(data));
	}
}