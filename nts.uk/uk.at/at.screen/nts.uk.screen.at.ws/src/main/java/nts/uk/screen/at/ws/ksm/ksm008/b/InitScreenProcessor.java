package nts.uk.screen.at.ws.ksm.ksm008.b;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.ejb.Stateless;

@Stateless
public class InitScreenProcessor {

    public TargetOrgIdenInfor getTargetOrg() {
//        GetTargetIdentifiInforService.get();
        System.out.println("ok");
        return null;
    }

}
