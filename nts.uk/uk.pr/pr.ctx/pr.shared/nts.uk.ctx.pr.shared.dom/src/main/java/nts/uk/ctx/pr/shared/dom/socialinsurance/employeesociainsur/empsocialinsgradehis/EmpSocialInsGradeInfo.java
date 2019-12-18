package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * 社員社会保険等級情報
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpSocialInsGradeInfo extends AggregateRoot {
    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 社会保険報酬月額(実質)
     */
    private MonthlyRemuneration socInsMonthlyRemune;

    /**
     * 算定区分
     */
    private CalculationAtr calculationAtr;

    /**
     * 健康保険標準報酬月額
     */
    private Optional<MonthlyRemuneration> healInsStandMonthlyRemune;

    /**
     * 健康保険等級
     */
    private Optional<InsuranceGrade> healInsGrade;

    /**
     * 厚生年金保険標準報酬月額
     */
    private Optional<MonthlyRemuneration> pensionInsStandCompenMonthly;

    /**
     * 厚生年金保険等級
     */
    private Optional<InsuranceGrade> pensionInsGrade;

    public EmpSocialInsGradeInfo(String hisId, Integer socInsMonthlyRemune, Integer calculationAtr, Integer healInsStandMonthlyRemune, Integer healInsGrade, Integer pensionInsStandCompenMonthly, Integer pensionInsGrade) {
        this.histId = hisId;
        this.socInsMonthlyRemune = new MonthlyRemuneration(socInsMonthlyRemune);
        this.calculationAtr = EnumAdaptor.valueOf(calculationAtr, CalculationAtr.class);
        this.healInsStandMonthlyRemune = healInsStandMonthlyRemune == null ? Optional.empty() : Optional.of(new MonthlyRemuneration(healInsStandMonthlyRemune));
        this.healInsGrade = healInsGrade == null ? Optional.empty() : Optional.of(new InsuranceGrade(healInsGrade));
        this.pensionInsStandCompenMonthly = pensionInsStandCompenMonthly == null ? Optional.empty() : Optional.of(new MonthlyRemuneration(pensionInsStandCompenMonthly));
        this.pensionInsGrade = pensionInsGrade == null ? Optional.empty() : Optional.of(new InsuranceGrade(pensionInsGrade));

    }
}
