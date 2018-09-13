package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtMonthlyHealthInsuranceCompensation;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtMonthlyHealthInsuranceCompensationPk;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaMonthlyHealthInsuranceCompensationRepository extends JpaRepository implements MonthlyHealthInsuranceCompensationRepository {

    private static final String GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID = "SELECT a FROM QpbmtHealthInsuranceStandardGradePerMonth a WHERE x.healthStdGraMonPk.targetStartYm=:targetStartYm AND x.healthStdGraMonPk.targetEndYm=:targetEndYm";

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
}
