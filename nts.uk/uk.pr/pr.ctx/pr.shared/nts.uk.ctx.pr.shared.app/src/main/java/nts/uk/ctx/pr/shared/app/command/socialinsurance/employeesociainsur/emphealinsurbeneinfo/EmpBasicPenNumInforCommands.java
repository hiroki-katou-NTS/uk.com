package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;

@Value
public class EmpBasicPenNumInforCommands {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 基礎年金番号
     */
    private String basicPenNumber;

    public EmpBasicPenNumInfor fromCommandToDomain(){
        return new EmpBasicPenNumInfor(employeeId, basicPenNumber);
    }
}
