package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateEmpSocialInsGradeInforCommand {

    /**社員ID*/
    @PeregEmployeeId
    private String employeeId;

    /** 履歴ID */
    @PeregRecordId
    private String historyId;

    /** 期間.開始年月 */
    @PeregItem("IS01016")
    private Integer startYM;

    /** 期間.終了年月 */
    @PeregItem("IS01017")
    private Integer endYM;

    /** 現在の等級 */
    @PeregItem("IS01018")
    private Integer currentGrade;

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
}
