package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtMonthlyHealthInsuranceCompensation;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtMonthlyHealthInsuranceCompensationPk;

@Stateless
public class JpaMonthlyHealthInsuranceCompensationRepository extends JpaRepository implements MonthlyHealthInsuranceCompensationRepository {

    private static final String GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID = "SELECT a FROM QpbmtHealthInsuranceGradePerRewardMonthlyRange a WHERE a.healthRewardRangePk.targetStartYm=:targetStartYm AND a.healthRewardRangePk.targetEndYm=:targetEndYm";
    private static final String GET_MONTHLY_HEALTH_INSU_COMPEN_BY_DATE = "SELECT a FROM QpbmtMonthlyHealthInsuranceCompensation a WHERE a.monHealthInsComPk.targetStartYm <= :date AND :date <= a.monHealthInsComPk.targetEndYm";
    
    
    @Override
    public Optional<MonthlyHealthInsuranceCompensation> getMonthlyHealthInsuranceCompensationById(int targetStartYm, int targetEndYm) {
        Optional<QpbmtMonthlyHealthInsuranceCompensation> entity = this.queryProxy().find(new QpbmtMonthlyHealthInsuranceCompensationPk(targetStartYm, targetEndYm), QpbmtMonthlyHealthInsuranceCompensation.class);
        if (!entity.isPresent())
            return Optional.empty();
        List<QpbmtHealthInsuranceGradePerRewardMonthlyRange> details = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID, QpbmtHealthInsuranceGradePerRewardMonthlyRange.class)
                .setParameter("targetStartYm", targetStartYm)
                .setParameter("targetEndYm", targetEndYm)
                .getList();
        return Optional.of(toDomain(entity.get(), details));
    }

    /**
     * Convert entity to domain
     *
     * @param entity  QpbmtMonthlyHealthInsuranceCompensation
     * @param details List<QpbmtHealthInsuranceGradePerRewardMonthlyRange>
     * @return HealthInsuranceStandardMonthly
     */
    private MonthlyHealthInsuranceCompensation toDomain(QpbmtMonthlyHealthInsuranceCompensation entity, List<QpbmtHealthInsuranceGradePerRewardMonthlyRange> details) {
        return new MonthlyHealthInsuranceCompensation(
                entity.monHealthInsComPk.targetEndYm,
                entity.monHealthInsComPk.targetEndYm,
                details.stream().map(x -> new HealthInsuranceGradePerRewardMonthlyRange(x.healthRewardRangePk.healthInsuranceGrade, x.rewardMonthlyLowerLimit, x.rewardMonthlyUpperLimit)).collect(Collectors.toList()));
    }

	@Override
	public Optional<MonthlyHealthInsuranceCompensation> findByDate(int date) {
		Optional<QpbmtMonthlyHealthInsuranceCompensation> entity = this.queryProxy()
				.query(GET_MONTHLY_HEALTH_INSU_COMPEN_BY_DATE, QpbmtMonthlyHealthInsuranceCompensation.class)
				.setParameter("date", date).getSingle();
		if (!entity.isPresent())
			return Optional.empty();
		val monthlyHealthInsurance = entity.get();
		List<QpbmtHealthInsuranceGradePerRewardMonthlyRange> details = this.queryProxy()
				.query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID,
						QpbmtHealthInsuranceGradePerRewardMonthlyRange.class)
				.setParameter("targetStartYm", monthlyHealthInsurance.monHealthInsComPk.targetStartYm)
				.setParameter("targetEndYm", monthlyHealthInsurance.monHealthInsComPk.targetEndYm).getList();
		return Optional.of(toDomain(monthlyHealthInsurance, details));
	}
}
