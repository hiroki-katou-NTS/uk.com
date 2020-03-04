package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;

@Value
public class CredentialAcquisitionInfoCommand {
    private SocialInsurAcquisiInforCommand socialInsurAcquisiInforCommand;
    private EmpBasicPenNumInforCommand empBasicPenNumInforCommand;
    private MultiEmpWorkInfoCommand multiEmpWorkInfoCommand;
}
