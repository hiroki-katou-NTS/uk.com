package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
@NoArgsConstructor
public class EmpSocialInsGradeInforDto extends PeregDomainDto {

    /** 期間.開始年月 */
    @PeregItem("IS01016")
    private GeneralDate startYM;

    /** 期間.終了年月 */
    @PeregItem("IS01017")
    private GeneralDate endYM;

    /** 現在の等級 */
    @PeregItem("IS01018")
    private String currentGrade;

    /** 算定区分 */
    @PeregItem("IS01019")
    private Integer calculationAtr;

    /** 健康保険等級 */
    @PeregItem("IS01020")
    private Integer healInsGrade;

    /** 健康保険標準報酬月額 */
    @PeregItem("IS01021")
    private Integer healInsStandMonthlyRemune;

    /** 厚生年金保険等級 */
    @PeregItem("IS01022")
    private Integer pensionInsGrade;

    /** 厚生年金保険標準報酬月額 */
    @PeregItem("IS01023")
    private Integer pensionInsStandCompenMonthly;

    /** 社会保険報酬月額（実質） */
    @PeregItem("IS01024")
    private Integer socInsMonthlyRemune;

    public EmpSocialInsGradeInforDto(String recordId, GeneralDate startYM, GeneralDate endYM, String currentGrade, Integer calculationAtr, Integer healInsGrade, Integer healInsStandMonthlyRemune, Integer pensionInsGrade, Integer pensionInsStandCompenMonthly, Integer socInsMonthlyRemune) {
        super(recordId);
        this.startYM = startYM;
        this.endYM = endYM;
        this.currentGrade = currentGrade;
        this.calculationAtr = calculationAtr;
        this.healInsGrade = healInsGrade;
        this.healInsStandMonthlyRemune = healInsStandMonthlyRemune;
        this.pensionInsGrade = pensionInsGrade;
        this.pensionInsStandCompenMonthly = pensionInsStandCompenMonthly;
        this.socInsMonthlyRemune = socInsMonthlyRemune;
    }

    public static EmpSocialInsGradeInforDto fromDomain(EmpSocialInsGradeHis domain, EmpSocialInsGradeInfo info, String currentGrade) {
        if (domain == null || info == null) {
            return null;
        }
        YearMonthHistoryItem period = domain.getYearMonthHistoryItems().get(0);
        if (period == null) {
            return null;
        }
        return new EmpSocialInsGradeInforDto(
                period.identifier(),
                GeneralDate.ymd(period.span().start().year(),period.span().start().month(),1),
                GeneralDate.ymd(period.span().end().year(),period.span().end().month(),1),
                currentGrade,
                info.getCalculationAtr().value,
                info.getHealInsGrade().map(PrimitiveValueBase::v).orElse(null),
                info.getHealInsStandMonthlyRemune().map(PrimitiveValueBase::v).orElse(null),
                info.getPensionInsGrade().map(PrimitiveValueBase::v).orElse(null),
                info.getPensionInsStandCompenMonthly().map(PrimitiveValueBase::v).orElse(null),
                info.getSocInsMonthlyRemune().v());
    }
}
