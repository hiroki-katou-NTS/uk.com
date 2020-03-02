package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class SocialInsurAcquisiInforCommands {

    /**
     * 社員ID
     */
    private String employeeId;


    private int continReemAfterRetirement;

}
