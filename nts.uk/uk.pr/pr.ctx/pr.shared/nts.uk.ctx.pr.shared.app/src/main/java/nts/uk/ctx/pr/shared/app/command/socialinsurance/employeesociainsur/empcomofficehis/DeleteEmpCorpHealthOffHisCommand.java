package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Data;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Data
public class DeleteEmpCorpHealthOffHisCommand {

    /**
     * 社員ID
     */
    @PeregEmployeeId
    private String employeeId;

    /**
     * 履歴ID
     */
    @PeregRecordId
    private String histId;

}
