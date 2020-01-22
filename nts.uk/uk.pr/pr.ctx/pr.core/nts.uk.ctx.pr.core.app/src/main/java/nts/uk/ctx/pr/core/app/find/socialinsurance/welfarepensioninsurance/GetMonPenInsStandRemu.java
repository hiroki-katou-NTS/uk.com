package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensStandGradePerMonthDto;
import nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.HealthInsStandardMonthlyInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.MonthlyScopeOfWelfarePensionCompensation;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionGradePerRewardMonthlyRange;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardGradePerMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;

@Stateless
public class GetMonPenInsStandRemu {

    @Inject
    private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

    // 等級から厚生年金保険標準報酬月額を取得する - IS01022
    public Long getMonthlyPensionInsStandardRemuneration(HealthInsStandardMonthlyInformation param) {
        Long standardMonthlyFee = null;

        if (param.getStartYM() != null) {
            // ドメインモデル「厚生年金標準月額」を取得する
            Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
                    .getWelfarePensionStandardMonthlyFeeByStartYearMonth(param.getStartYM().yearMonth().v());

            if (welfarePensionStandardMonthlyFee.isPresent()) {
                standardMonthlyFee = welfarePensionStandardMonthlyFee.get().getStandardMonthlyPrice().stream()
                        .filter(x -> param.getPensionInsGrade() != null && x.getWelfarePensionGrade() == param.getPensionInsGrade())
                        .map(xm -> xm.getStandardMonthlyFee())
                        .findFirst().orElse(null);
            }
        }

        return standardMonthlyFee;
    }

    // 報酬月額から厚生年金保険標準報酬月額と厚生年金保険等級を取得する
    public WelfarePensStandGradePerMonthDto getWelfarePensionStandardGradePerMonth(HealthInsStandardMonthlyInformation param) {

        WelfarePensStandGradePerMonthDto perMonthDto = new WelfarePensStandGradePerMonthDto();
        if (param.getStartYM() != null) {
            // ドメインモデル「厚生年金報酬月額範囲」を取得する
            Optional<MonthlyScopeOfWelfarePensionCompensation> dataMonth = welfarePensionStandardMonthlyFeeRepository
                    .getWelfarePensionStandardMonthlyFeeByStartYearMonthCom(param.getStartYM().yearMonth().v());

            // 取得した等級毎月額報酬範囲からパラメータ.報酬月額を含む厚生年金保険等級を取得する
            Optional<WelfarePensionGradePerRewardMonthlyRange> monthlyRanges;
            if (dataMonth.isPresent()) {
                monthlyRanges = dataMonth.get().getWelfarePensionGradePerRewardMonthlyRange().stream()
                        .filter(x -> param.getPensionInsStandCompenMonthly() != null && x.getRewardMonthlyLowerLimit() <= param.getPensionInsStandCompenMonthly() && param.getPensionInsStandCompenMonthly() <= x.getRewardMonthlyUpperLimit())
                        .findFirst();

                if (!monthlyRanges.isPresent()) {
                    val min = Collections.min(dataMonth.get().getWelfarePensionGradePerRewardMonthlyRange().stream()
                            .map(WelfarePensionGradePerRewardMonthlyRange::getRewardMonthlyLowerLimit).collect(Collectors.toList()));

                    if (param.getPensionInsStandCompenMonthly() != null && min > param.getPensionInsStandCompenMonthly()) {
                        perMonthDto.setWelfarePensionGrade(1);
                    } else if (param.getPensionInsStandCompenMonthly() != null && min < param.getPensionInsStandCompenMonthly()) {

                        perMonthDto.setWelfarePensionGrade(
                                Collections.max(dataMonth.get().getWelfarePensionGradePerRewardMonthlyRange().stream()
                                        .map(WelfarePensionGradePerRewardMonthlyRange::getWelfarePensionGrade).collect(Collectors.toList()))
                        );

                    }
                } else {
                    perMonthDto.setWelfarePensionGrade(monthlyRanges.get().getWelfarePensionGrade());
                }
            }

            // ドメインモデル「厚生年金標準月額」を取得する
            Optional<WelfarePensionStandardMonthlyFee> pensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
                    .getWelfarePensionStandardMonthlyFeeByStartYearMonth(param.getStartYM().yearMonth().v());

            pensionStandardMonthlyFee.ifPresent(welfarePensionStandardMonthlyFee ->
                    perMonthDto.setStandardMonthlyFee(
                            welfarePensionStandardMonthlyFee.getStandardMonthlyPrice().stream()
                                    .filter(x -> perMonthDto.getWelfarePensionGrade() != null && x.getWelfarePensionGrade() == perMonthDto.getWelfarePensionGrade())
                                    .map(WelfarePensionStandardGradePerMonth::getStandardMonthlyFee)
                                    .findFirst().orElse(null)
                    ));
        }

        return perMonthDto;
    }
}
