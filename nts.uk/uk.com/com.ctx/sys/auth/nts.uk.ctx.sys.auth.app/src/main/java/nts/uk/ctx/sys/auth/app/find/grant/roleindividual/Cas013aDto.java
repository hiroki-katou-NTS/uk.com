package nts.uk.ctx.sys.auth.app.find.grant.roleindividual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class Cas013aDto {
    private String companyID;

    private GeneralDate startValidPeriod;

    private GeneralDate endValidPeriod;

    private String employeeId;

    private String employeeCode;

    private String businessName;
}
