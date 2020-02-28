package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.WelfPenInsLossIfCommand;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;

@Value
public class LossInfoCommand {

    private HealthInsLossInfoCommand healthInsLossInfo;

    private WelfPenInsLossIfCommand welfPenInsLossIf;

    private EmpBasicPenNumInforCommands empBasicPenNumInfor;

    private MultiEmpWorkInfoCommands multiEmpWorkInfo ;

    private SocialInsurAcquisiInforCommands socialInsurAcquisiInfo;

    private int screenMode;



}
