package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance;

import lombok.val;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuranceStandardGradePerMonthDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.WelfarePensionStandardGradePerMonthDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.*;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetHealInsStandCompMonth {

    @Inject
    private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;

    @Inject
    private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;


    // 等級から健康保険標準報酬月額を取得する
    public long getHealInsStandCompMonth(HealthInsStandardMonthlyInformation param) {
        long standardMonthlyFee = 0L;

        // ドメインモデル「健康保険標準月額」を取得する
        Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(param.getStartYM());

        if (healthInsStandardMonthly.isPresent()) {
            standardMonthlyFee = healthInsStandardMonthly.get().getStandardGradePerMonth().stream()
                    .filter(x -> x.getHealthInsuranceGrade() == param.getHealInsGrade())
                    .map(xm -> xm.getStandardMonthlyFee())
                    .findFirst().orElse(0L);
        }

        return standardMonthlyFee;
    }

    // 報酬月額から健康保険標準報酬月額と健康保険等級を取得する
    public HealthInsuranceStandardGradePerMonthDto getHealthInsuranceStandardGradePerMonth(HealthInsStandardMonthlyInformation param) {

        HealthInsuranceStandardGradePerMonthDto perMonthDto = new HealthInsuranceStandardGradePerMonthDto();

        // ドメインモデル「健康保険報酬月額範囲」を取得する
        Optional<MonthlyHealthInsuranceCompensation> dataMonth = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonthCom(param.getStartYM());

        // 取得した等級毎月額報酬範囲からパラメータ.報酬月額を含む健康保険等級を取得する
        Optional<HealthInsuranceGradePerRewardMonthlyRange> monthlyRanges;
        if (dataMonth.isPresent()) {
            monthlyRanges = dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                    .filter(x -> x.getRewardMonthlyLowerLimit() <= param.getHealInsStandMonthlyRemune() && param.getHealInsStandMonthlyRemune() <= x.getRewardMonthlyUpperLimit())
                    .findFirst();

            if (!monthlyRanges.isPresent()) {
                val min = Collections.min(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                        .map(HealthInsuranceGradePerRewardMonthlyRange::getRewardMonthlyLowerLimit).collect(Collectors.toList()));

                if (min > param.getHealInsStandMonthlyRemune()) {
                    perMonthDto.setHealthInsuranceGrade(1);
                } else if (min < param.getHealInsStandMonthlyRemune()) {

                    perMonthDto.setHealthInsuranceGrade(
                            Collections.max(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                            .map(HealthInsuranceGradePerRewardMonthlyRange::getHealthInsuranceGrade).collect(Collectors.toList()))
                    );

                }
            } else {
                perMonthDto.setHealthInsuranceGrade(monthlyRanges.get().getHealthInsuranceGrade());
            }
        }

        // ドメインモデル「健康保険標準月額」を取得する
        Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(param.getStartYM());
        healthInsStandardMonthly.ifPresent(healthInsuranceStandardMonthly ->
                perMonthDto.setStandardMonthlyFee(
                    healthInsuranceStandardMonthly.getStandardGradePerMonth().stream()
                            .filter(x -> x.getHealthInsuranceGrade() == perMonthDto.getHealthInsuranceGrade())
                            .map(HealthInsuranceStandardGradePerMonth::getStandardMonthlyFee)
                            .findFirst().orElse(0L)
        ));

        return perMonthDto;
    }

    // 等級から厚生年金保険標準報酬月額を取得する
    public long getMonthlyPensionInsStandardRemuneration(HealthInsStandardMonthlyInformation param) {
        long standardMonthlyFee = 0L;

        // ドメインモデル「厚生年金標準月額」を取得する
        Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
                .getWelfarePensionStandardMonthlyFeeByStartYearMonth(param.getStartYM());

        if (welfarePensionStandardMonthlyFee.isPresent()) {
            standardMonthlyFee = welfarePensionStandardMonthlyFee.get().getStandardMonthlyPrice().stream()
                    .filter(x -> x.getWelfarePensionGrade() == param.getPensionInsGrade())
                    .map(xm -> xm.getStandardMonthlyFee())
                    .findFirst().orElse(0L);
        }

        return standardMonthlyFee;
    }


    // 報酬月額から厚生年金保険標準報酬月額と厚生年金保険等級を取得する
    public WelfarePensionStandardGradePerMonthDto getWelfarePensionStandardGradePerMonth(HealthInsStandardMonthlyInformation param) {

        WelfarePensionStandardGradePerMonthDto perMonthDto = new WelfarePensionStandardGradePerMonthDto();

        // ドメインモデル「厚生年金報酬月額範囲」を取得する
        Optional<MonthlyScopeOfWelfarePensionCompensation> dataMonth = welfarePensionStandardMonthlyFeeRepository
                .getWelfarePensionStandardMonthlyFeeByStartYearMonthCom(param.getStartYM());

        // 取得した等級毎月額報酬範囲からパラメータ.報酬月額を含む厚生年金保険等級を取得する
        Optional<WelfarePensionGradePerRewardMonthlyRange> monthlyRanges;
        if (dataMonth.isPresent()) {
            monthlyRanges = dataMonth.get().getWelfarePensionGradePerRewardMonthlyRange().stream()
                    .filter(x -> x.getRewardMonthlyLowerLimit() <= param.getPensionInsStandCompenMonthly() && param.getPensionInsStandCompenMonthly() <= x.getRewardMonthlyUpperLimit())
                    .findFirst();

            if (!monthlyRanges.isPresent()) {
                val min = Collections.min(dataMonth.get().getWelfarePensionGradePerRewardMonthlyRange().stream()
                        .map(WelfarePensionGradePerRewardMonthlyRange::getRewardMonthlyLowerLimit).collect(Collectors.toList()));

                if (min > param.getPensionInsStandCompenMonthly()) {
                    perMonthDto.setWelfarePensionGrade(1);
                } else if (min < param.getHealInsStandMonthlyRemune()) {

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
                .getWelfarePensionStandardMonthlyFeeByStartYearMonth(param.getStartYM());

        pensionStandardMonthlyFee.ifPresent(welfarePensionStandardMonthlyFee ->
                perMonthDto.setStandardMonthlyFee(
                        welfarePensionStandardMonthlyFee.getStandardMonthlyPrice().stream()
                                .filter(x -> x.getWelfarePensionGrade() == perMonthDto.getWelfarePensionGrade())
                                .map(WelfarePensionStandardGradePerMonth::getStandardMonthlyFee)
                                .findFirst().orElse(0L)
                ));

        return perMonthDto;
    }
}
