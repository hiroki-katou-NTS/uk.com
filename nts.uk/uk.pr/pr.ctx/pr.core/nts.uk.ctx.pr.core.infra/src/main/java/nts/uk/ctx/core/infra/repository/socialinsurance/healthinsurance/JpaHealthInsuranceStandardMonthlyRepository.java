package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardMonthly;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardMonthlyPk;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtBonusEmployeePensionInsuranceRate;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 健康保険標準月額
 */
@Stateless
public class JpaHealthInsuranceStandardMonthlyRepository extends JpaRepository implements HealthInsuranceStandardMonthlyRepository {

    private static final String GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID = "SELECT a FROM QpbmtHealthInsuranceStandardGradePerMonth a WHERE x.healthStdGraMonPk.targetStartYm=:targetStartYm AND x.healthStdGraMonPk.targetEndYm=:targetEndYm";

    @Override
    public Optional<HealthInsuranceStandardMonthly> getHealthInsuranceStandardMonthlyById(int targetStartYm, int targetEndYm) {
        Optional<QpbmtHealthInsuranceStandardMonthly> entity = this.queryProxy().find(new QpbmtHealthInsuranceStandardMonthlyPk(targetStartYm, targetEndYm), QpbmtHealthInsuranceStandardMonthly.class);
        if (!entity.isPresent())
            return Optional.empty();

        List<QpbmtHealthInsuranceStandardGradePerMonth> details = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID, QpbmtHealthInsuranceStandardGradePerMonth.class)
                .getList();
        return Optional.of(toDomain(entity.get(), details));
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
