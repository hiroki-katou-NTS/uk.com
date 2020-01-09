package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteEmpHealInsQualifiInfoCommand {
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

}
