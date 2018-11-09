package nts.uk.ctx.pr.core.infra.repository.socialinsurance.healthinsurance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.*;

/**
 * 健康保険標準月額
 */
@Stateless
public class JpaHealthInsuranceStandardMonthlyRepository extends JpaRepository implements HealthInsuranceStandardMonthlyRepository {

    private static final String GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID = "SELECT a FROM QpbmtHealthInsuranceStandardGradePerMonth a WHERE a.targetStartYm <=:targetStartYm AND a.targetEndYm >=:targetStartYm order by a.healthStdGraMonPk.healthInsuranceGrade ASC ";

    @Override
    public Optional<HealthInsuranceStandardMonthly> getHealthInsuranceStandardMonthlyByStartYearMonth(int targetStartYm) {
        List<QpbmtHealthInsuranceStandardGradePerMonth> details = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID, QpbmtHealthInsuranceStandardGradePerMonth.class)
                .setParameter("targetStartYm", targetStartYm)
                .getList();
        if(details.isEmpty())
            return  Optional.empty();
        return Optional.of(toDomainHealth(details));
    }

    @Override
    public Optional<MonthlyHealthInsuranceCompensation> getHealthInsuranceStandardMonthlyByStartYearMonthCom(int targetStartYm) {
        List<QpbmtHealthInsuranceStandardGradePerMonth> details = this.queryProxy().query(GET_HEALTH_INSURANCE_STANDARD_GRADE_PER_MONTH_BY_ID, QpbmtHealthInsuranceStandardGradePerMonth.class)
                .setParameter("targetStartYm", targetStartYm)
                .getList();
        if(details.isEmpty())
            return  Optional.empty();
        return Optional.of(toDomainMon(details));
    }

    /**
     * Convert entity to domain
     *
     * @param details List<QpbmtHealthInsuranceStandardGradePerMonth>
     * @return HealthInsuranceStandardMonthly
     */
    private HealthInsuranceStandardMonthly toDomainHealth (List<QpbmtHealthInsuranceStandardGradePerMonth> details) {
        return new HealthInsuranceStandardMonthly(
                details.get(0).targetStartYm,
                details.get(0).targetEndYm,
                details.stream().map(x -> new HealthInsuranceStandardGradePerMonth(x.healthStdGraMonPk.healthInsuranceGrade, x.standardMonthlyFee)).collect(Collectors.toList()));
    }

    private MonthlyHealthInsuranceCompensation toDomainMon (List<QpbmtHealthInsuranceStandardGradePerMonth> details) {
        return new MonthlyHealthInsuranceCompensation(
                details.get(0).targetStartYm,
                details.get(0).targetEndYm,
                details.stream().map(x -> new HealthInsuranceGradePerRewardMonthlyRange(x.healthStdGraMonPk.healthInsuranceGrade,x.rewardMonthlyLowerLimit, x.rewardMonthlyUpperLimit)).collect(Collectors.toList()));
    }

}
