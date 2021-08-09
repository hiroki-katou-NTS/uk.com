package nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePosition {

    /** 社員ID **/
    private String empID;
    /**	職位ID **/
    private String jobtitleID;

    private String jobtitleCode;

}