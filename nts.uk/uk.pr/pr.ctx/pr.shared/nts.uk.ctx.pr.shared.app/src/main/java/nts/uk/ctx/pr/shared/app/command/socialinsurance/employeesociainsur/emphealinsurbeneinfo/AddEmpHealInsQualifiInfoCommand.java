package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class AddEmpHealInsQualifiInfoCommand {
    /**
     * 社員ID
     */
    @PeregEmployeeId
    private String employeeId;

    /**
     * 履歴ID
     */
    @PeregRecordId
    private String historyId;

    /**
     * 得喪期間.期間.開始日
     */
    @PeregItem("IS00841")
    private GeneralDate startDate;

    /**
     * 得喪期間.期間.終了日
     */
    @PeregItem("IS00842")
    private GeneralDate endDate;

    /**
     * 健康保険番号
     */
    @PeregItem("IS00843")
    private String healInsNumber;

    /**
     * 介護保険番号
     */
    @PeregItem("IS00844")
    private String nurCaseInsNumber;
}
