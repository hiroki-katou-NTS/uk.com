package nts.uk.screen.at.ws.ksm.ksm008.b;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/ksm008/screenb")
@Produces(MediaType.APPLICATION_JSON)
public class initScreeen {

    @Inject
    private InitScreenProcessor initScreenProcessor;

    @Path("init")
    public TargetOrgIdenInfor init() {
        return initScreenProcessor.getTargetOrg();
    }

}
