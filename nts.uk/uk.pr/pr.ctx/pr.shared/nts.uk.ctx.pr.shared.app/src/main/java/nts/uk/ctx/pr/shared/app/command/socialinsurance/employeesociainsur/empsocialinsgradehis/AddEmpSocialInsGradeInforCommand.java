package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

import java.math.BigDecimal;

@Getter
public class AddEmpSocialInsGradeInforCommand {

    /**社員ID*/
    @PeregEmployeeId
    private String employeeId;

    /** 履歴ID */
    @PeregRecordId
    private String historyId;

    /** 期間.開始年月 */
    @PeregItem("IS01016")
    private GeneralDate startYM;

    /** 期間.終了年月 */
    @PeregItem("IS01017")
    private GeneralDate endYM;

    /** 現在の等級
    @PeregItem("IS01018")
    private BigDecimal currentGrade;*/

    /** 算定区分 */
    @PeregItem("IS01019")
    private BigDecimal calculationAtr;

    /** 健康保険等級 */
    @PeregItem("IS01020")
    private BigDecimal healInsGrade;

    /** 健康保険標準報酬月額 */
    @PeregItem("IS01021")
    private BigDecimal healInsStandMonthlyRemune;

    /** 厚生年金保険等級 */
    @PeregItem("IS01022")
    private BigDecimal pensionInsGrade;

    /** 厚生年金保険標準報酬月額 */
    @PeregItem("IS01023")
    private BigDecimal pensionInsStandCompenMonthly;

    /** 社会保険報酬月額（実質） */
    @PeregItem("IS01024")
    private BigDecimal socInsMonthlyRemune;
}
