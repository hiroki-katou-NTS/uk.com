package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.StandardGradePerMonth;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardGradePerMonth;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardMonthlyFeePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaWelfarePensionStandardMonthlyFeeRepository extends JpaRepository implements WelfarePensionStandardMonthlyFeeRepository {


    /**
     * Convert entity to domain
     *
     * @param entity                      QpbmtWelfarePensionStandardMonthlyFee
     * @param standardGradePerMonthEntity QpbmtWelfarePensionStandardGradePerMonth
     * @return WelfarePensionStandardMonthlyFee
     */
    private WelfarePensionStandardMonthlyFee toDomain(QpbmtWelfarePensionStandardMonthlyFee entity, List<QpbmtWelfarePensionStandardGradePerMonth> standardGradePerMonthEntity) {
        return new WelfarePensionStandardMonthlyFee(entity.welfareStdMonFeePk.targetStartYm, entity.welfareStdMonFeePk.targetEndYm, standardGradePerMonthEntity.stream().map(x -> new StandardGradePerMonth(x.penStdGraMonPk.welfarePensionGrade, x.standardMonthlyFee)).collect(Collectors.toList()));
    }

    /**
     * Convert domain to entity
     *
     * @param domain WelfarePensionStandardMonthlyFee
     * @return QpbmtWelfarePensionStandardMonthlyFee
     */
    private static QpbmtWelfarePensionStandardMonthlyFee toEntity(WelfarePensionStandardMonthlyFee domain) {
        return new QpbmtWelfarePensionStandardMonthlyFee(new QpbmtWelfarePensionStandardMonthlyFeePk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v()));
    }
}
