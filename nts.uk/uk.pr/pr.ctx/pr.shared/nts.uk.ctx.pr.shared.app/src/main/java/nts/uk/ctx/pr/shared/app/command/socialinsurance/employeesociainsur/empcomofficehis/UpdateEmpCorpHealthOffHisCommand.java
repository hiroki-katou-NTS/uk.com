package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Data
public class UpdateEmpCorpHealthOffHisCommand {

    /**
     * 社員ID
     */
    @PeregEmployeeId
    private String employeeId;
    /**
     * 開始日
     */
    @PeregItem("IS00788")
    private GeneralDate startDate;
    /**
     * 終了日
     */
    @PeregItem("IS00789")
    private GeneralDate endDate;

    @PeregRecordId
    private String histId;

    @PeregItem("IS00790")
    private String code;

}
