package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionGradePerRewardMonthlyRange;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtMonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtMonthlyScopeOfWelfarePensionCompensationPk;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionGradePerRewardMonthlyRange;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardGradePerMonth;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardMonthlyFee;

import javax.ejb.Stateless;

import lombok.val;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaMonthlyScopeOfWelfarePensionCompensationRepository extends JpaRepository implements MonthlyScopeOfWelfarePensionCompensationRepository {
   
    private static final String GET_MONTH_BY_START_YEAR_MONTH = "SELECT a FROM QpbmtMonthlyScopeOfWelfarePensionCompensation a WHERE a.monRangeWelPenPk.targetStartYm <=:targetStartYm AND a.monRangeWelPenPk.targetEndYm >=:targetStartYm";
    private static final String GET_MONTH_SCOPE_OF_WELFARE_PENSION_BY_YEAR_MONTH = "SELECT a FROM QpbmtWelfarePensionGradePerRewardMonthlyRange a WHERE a.penRewardRangePk.targetStartYm=:targetStartYm AND a.penRewardRangePk.targetEndYm=:targetEndYm";
    																							  
	/**
     * Convert entity to domain
     *
     * @param entity               QpbmtMonthlyScopeOfWelfarePensionCompensation
     * @param gradePerRewardEntity QpbmtWelfarePensionGradePerRewardMonthlyRange
     * @return MonthlyScopeOfWelfarePensionCompensation
     */
    private MonthlyScopeOfWelfarePensionCompensation toDomain(QpbmtMonthlyScopeOfWelfarePensionCompensation entity, List<QpbmtWelfarePensionGradePerRewardMonthlyRange> gradePerRewardEntity) {
        return new MonthlyScopeOfWelfarePensionCompensation(entity.monRangeWelPenPk.targetStartYm, entity.monRangeWelPenPk.targetEndYm, gradePerRewardEntity.stream().map(x -> new WelfarePensionGradePerRewardMonthlyRange(x.penRewardRangePk.welfarePensionGrade, x.rewardMonthlyLowerLimit, x.rewardMonthlyUpperLimit)).collect(Collectors.toList()));
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

	@Override
	public Optional<MonthlyScopeOfWelfarePensionCompensation> getMonthlyScopeOfWelfarePensionCompensationByStartYearMonth(
			int startYearMonth) {
		Optional<QpbmtMonthlyScopeOfWelfarePensionCompensation> entityOptional = this.queryProxy().query(GET_MONTH_BY_START_YEAR_MONTH, QpbmtMonthlyScopeOfWelfarePensionCompensation.class)
                .setParameter("targetStartYm", startYearMonth)
                .getSingle();

        if (!entityOptional.isPresent())
            return Optional.empty();

        val entity = entityOptional.get();
        List<QpbmtWelfarePensionGradePerRewardMonthlyRange> details = this.queryProxy().query(GET_MONTH_SCOPE_OF_WELFARE_PENSION_BY_YEAR_MONTH, QpbmtWelfarePensionGradePerRewardMonthlyRange.class)
                .setParameter("targetStartYm", entity.monRangeWelPenPk.targetStartYm)
                .setParameter("targetEndYm", entity.monRangeWelPenPk.targetEndYm)
                .getList();

        return Optional.of(toDomain(entity, details));
	}
}
