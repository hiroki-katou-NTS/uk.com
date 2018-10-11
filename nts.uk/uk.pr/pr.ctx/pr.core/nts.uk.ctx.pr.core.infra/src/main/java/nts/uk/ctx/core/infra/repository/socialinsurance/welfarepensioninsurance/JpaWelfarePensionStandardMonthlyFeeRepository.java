package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardGradePerMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardGradePerMonth;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardMonthlyFeePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaWelfarePensionStandardMonthlyFeeRepository extends JpaRepository implements WelfarePensionStandardMonthlyFeeRepository {

    private static final String GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_START_YEAR_MONTH = "SELECT a FROM QpbmtWelfarePensionStandardMonthlyFee a WHERE a.welfareStdMonFeePk.targetStartYm <=:targetStartYm AND a.welfareStdMonFeePk.targetEndYm >=:targetStartYm";
    private static final String GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_YEAR_MONTH = "SELECT a FROM QpbmtWelfarePensionStandardGradePerMonth a WHERE a.penStdGraMonPk.targetStartYm=:targetStartYm AND a.penStdGraMonPk.targetEndYm=:targetEndYm order by a.penStdGraMonPk.welfarePensionGrade ASC";

    @Override
    public Optional<WelfarePensionStandardMonthlyFee> getWelfarePensionStandardMonthlyFeeByStartYearMonth(int startYearMonth) {
        Optional<QpbmtWelfarePensionStandardMonthlyFee> entityOptional = this.queryProxy().query(GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_START_YEAR_MONTH, QpbmtWelfarePensionStandardMonthlyFee.class)
                .setParameter("targetStartYm", startYearMonth)
                .getSingle();

        if (!entityOptional.isPresent())
            return Optional.empty();

        val entity = entityOptional.get();
        List<QpbmtWelfarePensionStandardGradePerMonth> details = this.queryProxy().query(GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_YEAR_MONTH, QpbmtWelfarePensionStandardGradePerMonth.class)
                .setParameter("targetStartYm", entity.welfareStdMonFeePk.targetStartYm)
                .setParameter("targetEndYm", entity.welfareStdMonFeePk.targetEndYm)
                .getList();

        return Optional.of(toDomain(entity, details));
    }

    /**
     * Convert entity to domain
     *
     * @param entity                      QpbmtWelfarePensionStandardMonthlyFee
     * @param standardGradePerMonthEntity QpbmtWelfarePensionStandardGradePerMonth
     * @return WelfarePensionStandardMonthlyFee
     */
    private WelfarePensionStandardMonthlyFee toDomain(QpbmtWelfarePensionStandardMonthlyFee entity, List<QpbmtWelfarePensionStandardGradePerMonth> standardGradePerMonthEntity) {
        return new WelfarePensionStandardMonthlyFee(entity.welfareStdMonFeePk.targetStartYm, entity.welfareStdMonFeePk.targetEndYm, standardGradePerMonthEntity.stream().map(x -> new WelfarePensionStandardGradePerMonth(x.penStdGraMonPk.welfarePensionGrade, x.standardMonthlyFee)).collect(Collectors.toList()));
    }

    /**
     * Convert domain to entity
     *
     * @param domain WelfarePensionStandardMonthlyFee
     * @return QpbmtWelfarePensionStandardMonthlyFee
     */
    private QpbmtWelfarePensionStandardMonthlyFee toEntity(WelfarePensionStandardMonthlyFee domain) {
        return new QpbmtWelfarePensionStandardMonthlyFee(new QpbmtWelfarePensionStandardMonthlyFeePk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v()));
    }
}
