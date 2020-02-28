package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
@Value
public class MultiEmpWorkInfoCommands {
    /**
            * 社員ID
    */
    private String empId;

    /**
     * 二以上事業所勤務者
     */
    private int isMoreEmp;

     public MultiEmpWorkInfo fromCommandToDomain(){
         return new MultiEmpWorkInfo(empId, isMoreEmp);
     }
}
