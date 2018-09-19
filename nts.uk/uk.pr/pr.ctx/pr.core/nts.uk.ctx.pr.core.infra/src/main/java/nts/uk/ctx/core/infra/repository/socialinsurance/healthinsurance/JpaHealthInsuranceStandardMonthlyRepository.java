package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardMonthly;

/**
 * 健康保険標準月額
 */
@Stateless
public class JpaHealthInsuranceStandardMonthlyRepository extends JpaRepository implements HealthInsuranceStandardMonthlyRepository {

    private static final String GET_HEALTH_INSURANCE_STANDARD_MONTHLY_BY_START_YEAR_MONTH = "SELECT a FROM QpbmtHealthInsuranceStandardMonthly a WHERE a.healthInsStdMonPk.targetStartYm <=:targetStartYm AND a.healthInsStdMonPk.targetEndYm >=:targetStartYm";
    private static final String GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID = "SELECT a FROM QpbmtHealthInsuranceStandardGradePerMonth a WHERE a.healthStdGraMonPk.targetStartYm=:targetStartYm AND a.healthStdGraMonPk.targetEndYm=:targetEndYm";

    @Override
    public Optional<HealthInsuranceStandardMonthly> getHealthInsuranceStandardMonthlyByStartYearMonth(int targetStartYm) {
        Optional<QpbmtHealthInsuranceStandardMonthly> entity = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_MONTHLY_BY_START_YEAR_MONTH, QpbmtHealthInsuranceStandardMonthly.class)
                .setParameter("targetStartYm", targetStartYm)
                .getSingle();
        if (!entity.isPresent())
            return Optional.empty();

        val healthInsuranceStandardMonthlyEntity = entity.get();
        List<QpbmtHealthInsuranceStandardGradePerMonth> details = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID, QpbmtHealthInsuranceStandardGradePerMonth.class)
                .setParameter("targetStartYm", healthInsuranceStandardMonthlyEntity.healthInsStdMonPk.targetStartYm)
                .setParameter("targetEndYm", healthInsuranceStandardMonthlyEntity.healthInsStdMonPk.targetEndYm)
                .getList();
        return Optional.of(toDomain(healthInsuranceStandardMonthlyEntity, details));
    }

    /**
     * Convert entity to domain
     *
     * @param entity  QpbmtHealthInsuranceStandardMonthly
     * @param details List<QpbmtHealthInsuranceStandardGradePerMonth>
     * @return HealthInsuranceStandardMonthly
     */
    private HealthInsuranceStandardMonthly toDomain(QpbmtHealthInsuranceStandardMonthly entity, List<QpbmtHealthInsuranceStandardGradePerMonth> details) {
        return new HealthInsuranceStandardMonthly(
                entity.healthInsStdMonPk.targetEndYm,
                entity.healthInsStdMonPk.targetEndYm,
                details.stream().map(x -> new HealthInsuranceStandardGradePerMonth(x.healthStdGraMonPk.healthInsuranceGrade, x.standardMonthlyFee)).collect(Collectors.toList()));
    }
}
