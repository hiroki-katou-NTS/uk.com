package nts.uk.ctx.pr.report.ws.printdata.insurenamechangenoti;


import nts.uk.ctx.pr.report.app.command.socinsurnoticreset.SocialInsurNotiCreateSetCommand;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSetService;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printdata/insurenamechangenoti")
@Produces("application/json")
public class InsuredNameChangedNotiWebService {

    @Inject
    private SocialInsurNotiCreateSetService service;


    @POST
    @Path("/index")
    public void index(SocialInsurNotiCreateSetCommand command){
        String userID = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(
                userID,
                cid,
                command.getOfficeInformation(),
                command.getBusinessArrSymbol(),
                command.getOutputOrder(),
                command.getPrintPersonNumber(),
                0,
                command.getInsuredNumber(),
                null,null,
                null,
                null

        );
        service.checkSocialInsurNotiCrSet(domain,userID,cid);
    }
}
