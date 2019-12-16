package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
@NoArgsConstructor
public class EmpSocialInsGradeInforDto extends PeregDomainDto {

    @PeregItem("IS01016")
    private int startYM;

    @PeregItem("IS01017")
    private int endYM;

    @PeregItem("IS01018")
    private int currentGrade;

    @PeregItem("IS01019")
    private int calculationAtr;

    @PeregItem("IS01020")
    private int healInsGrade;

    @PeregItem("IS01021")
    private int healInsStandMonthlyRemune;

    @PeregItem("IS01022")
    private int pensionInsGrade;

    @PeregItem("IS01023")
    private int pensionInsStandCompenMonthly;

    @PeregItem("IS01024")
    private int socInsMonthlyRemune;

    public EmpSocialInsGradeInforDto(String recordId, int startYM, int endYM, int calculationAtr, int healInsGrade, int healInsStandMonthlyRemune, int pensionInsGrade, int pensionInsStandCompenMonthly, int socInsMonthlyRemune) {
        super(recordId);
        this.startYM = startYM;
        this.endYM = endYM;
        this.calculationAtr = calculationAtr;
        this.healInsGrade = healInsGrade;
        this.healInsStandMonthlyRemune = healInsStandMonthlyRemune;
        this.pensionInsGrade = pensionInsGrade;
        this.pensionInsStandCompenMonthly = pensionInsStandCompenMonthly;
        this.socInsMonthlyRemune = socInsMonthlyRemune;
    }

    public static EmpSocialInsGradeInforDto fromDomain(EmpSocialInsGradeHis domain, EmpSocialInsGradeInfo info) {
        if (domain == null || info == null) {
            return null;
        }
        YearMonthHistoryItem period = domain.getPeriod().get(0);
        if (period == null) {
            return null;
        }
        return new EmpSocialInsGradeInforDto(
                period.identifier(),
                period.span().start().v(),
                period.span().end().v(),
                info.getCalculationAtr().value,
                info.getHealInsGrade().map(x -> x.v()).orElse(null),
                info.getHealInsStandMonthlyRemune().map(x -> x.v()).orElse(null),
                info.getPensionInsGrade().map(x -> x.v()).orElse(null),
                info.getPensionInsStandCompenMonthly().map(x -> x.v()).orElse(null),
                info.getSocInsMonthlyRemune().v());
    }
}
