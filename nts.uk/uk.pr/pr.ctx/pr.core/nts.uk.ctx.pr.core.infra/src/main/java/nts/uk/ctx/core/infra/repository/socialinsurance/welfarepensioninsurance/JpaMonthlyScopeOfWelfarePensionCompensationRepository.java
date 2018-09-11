package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.GradePerRewardMonthlyRange;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtMonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtMonthlyScopeOfWelfarePensionCompensationPk;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionGradePerRewardMonthlyRange;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaMonthlyScopeOfWelfarePensionCompensationRepository extends JpaRepository implements MonthlyScopeOfWelfarePensionCompensationRepository {
    /**
     * Convert entity to domain
     *
     * @param entity               QpbmtMonthlyScopeOfWelfarePensionCompensation
     * @param gradePerRewardEntity QpbmtWelfarePensionGradePerRewardMonthlyRange
     * @return MonthlyScopeOfWelfarePensionCompensation
     */
    private MonthlyScopeOfWelfarePensionCompensation toDomain(QpbmtMonthlyScopeOfWelfarePensionCompensation entity, List<QpbmtWelfarePensionGradePerRewardMonthlyRange> gradePerRewardEntity) {
        return new MonthlyScopeOfWelfarePensionCompensation(entity.monRangeWelPenPk.targetStartYm, entity.monRangeWelPenPk.targetEndYm, gradePerRewardEntity.stream().map(x -> new GradePerRewardMonthlyRange(x.penRewardRangePk.welfarePensionGrade, x.rewardMonthlyLowerLimit, x.rewardMonthlyUpperLimit)).collect(Collectors.toList()));
    }

    /**
     * Convert domain to entity
     *
     * @param domain MonthlyScopeOfWelfarePensionCompensation
     * @return QpbmtMonthlyScopeOfWelfarePensionCompensation
     */
    private static QpbmtMonthlyScopeOfWelfarePensionCompensation toEntity(MonthlyScopeOfWelfarePensionCompensation domain) {
        return new QpbmtMonthlyScopeOfWelfarePensionCompensation(new QpbmtMonthlyScopeOfWelfarePensionCompensationPk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v()));
    }
}
