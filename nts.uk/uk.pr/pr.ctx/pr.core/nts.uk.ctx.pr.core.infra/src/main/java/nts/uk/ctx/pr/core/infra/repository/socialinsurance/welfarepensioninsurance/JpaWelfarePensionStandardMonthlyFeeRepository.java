package nts.uk.ctx.pr.core.infra.repository.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionStandardGradePerMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.*;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaWelfarePensionStandardMonthlyFeeRepository extends JpaRepository implements WelfarePensionStandardMonthlyFeeRepository {


    private static final String GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_YEAR_MONTH = "SELECT a FROM QpbmtWelfarePensionStandardGradePerMonth a WHERE a.targetStartYm <=:targetStartYm AND a.targetEndYm >=:targetStartYm order by a.penStdGraMonPk.welfarePensionGrade ASC";

    @Override
    public Optional<WelfarePensionStandardMonthlyFee> getWelfarePensionStandardMonthlyFeeByStartYearMonth(int startYearMonth) {

        List<QpbmtWelfarePensionStandardGradePerMonth> details = this.queryProxy().query(GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_YEAR_MONTH, QpbmtWelfarePensionStandardGradePerMonth.class)
                .setParameter("targetStartYm",startYearMonth)
                .getList();
        if(details.isEmpty())
            return Optional.empty();
        return Optional.of(toDomain(details));
    }


    @Override
    public Optional<MonthlyScopeOfWelfarePensionCompensation> getWelfarePensionStandardMonthlyFeeByStartYearMonthCom(int startYearMonth) {

        List<QpbmtWelfarePensionStandardGradePerMonth> details = this.queryProxy().query(GET_WELFARE_PENSION_STANDARD_MONTHLY_FEE_BY_YEAR_MONTH, QpbmtWelfarePensionStandardGradePerMonth.class)
                .setParameter("targetStartYm",startYearMonth)
                .getList();
        if(details.isEmpty())
            return Optional.empty();
        return Optional.of(toDomainCom(details));
    }

    /**
     * Convert entity to domain
     *
     * @param standardGradePerMonthEntity QpbmtWelfarePensionStandardGradePerMonth
     * @return WelfarePensionStandardMonthlyFee
     */
    private WelfarePensionStandardMonthlyFee toDomain( List<QpbmtWelfarePensionStandardGradePerMonth> standardGradePerMonthEntity) {
        return new WelfarePensionStandardMonthlyFee(standardGradePerMonthEntity.get(0).targetStartYm, standardGradePerMonthEntity.get(0).targetEndYm, standardGradePerMonthEntity.stream().map(x -> new WelfarePensionStandardGradePerMonth(x.penStdGraMonPk.welfarePensionGrade, x.standardMonthlyFee)).collect(Collectors.toList()));
    }



    private MonthlyScopeOfWelfarePensionCompensation toDomainCom(List<QpbmtWelfarePensionStandardGradePerMonth> standardGradePerMonthEntity) {
        return new MonthlyScopeOfWelfarePensionCompensation(standardGradePerMonthEntity.get(0).targetStartYm, standardGradePerMonthEntity.get(0).targetEndYm, standardGradePerMonthEntity.stream().map(x -> new WelfarePensionGradePerRewardMonthlyRange(x.penStdGraMonPk.welfarePensionGrade, x.rewardMonthlyLowerLimit, x.rewardMonthlyUpperLimit)).collect(Collectors.toList()));
    }
}
