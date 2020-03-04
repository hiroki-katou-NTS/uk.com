package nts.uk.ctx.pr.report.ws.printconfig.socinsurnoticreset;

import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.SocialInsurNotiCreateSetCommand;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpNameChangeNotiInforDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpNameChangeNotiInforFinder;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.SocialInsurNotiCreateSetDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.SocialInsurNotiCreateSetFinder;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSetService;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/insurenamechangenoti")
@Produces("application/json")
public class InsuredNameChangedNotiWebService {

    @Inject
    private SocialInsurNotiCreateSetService service;

    @Inject
    private EmpNameChangeNotiInforFinder empNameChangeNotiInforFinder;

    @Inject
    private SocialInsurNotiCreateSetFinder socialInsurNotiCreateSetFinder;

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
                command.getSubmittedName(),
                command.getInsuredNumber(),
                command.getFdNumber(),command.getTextPersonNumber(),
                command.getOutputFormat(),
                command.getLineFeedCode()

        );
        service.checkSocialInsurNotiCrSet(domain,userID,cid);
    }

    @POST
    @Path("/getEmpNameChangeNotiInfor/{empId}")
    public EmpNameChangeNotiInforDto getEmpNameChangeNotiInfor(@PathParam("empId") String empId ){
        String cid = AppContexts.user().companyId();
        EmpNameChangeNotiInfor empNameChangeNotiInfor = empNameChangeNotiInforFinder.getEmpNameChangeNotiInforById(empId,cid);
        return EmpNameChangeNotiInforDto.fromDomain(empNameChangeNotiInfor);
    }

    @POST
    @Path("/getSocialInsurNotiCreateSetById")
    public SocialInsurNotiCreateSetDto getSocialInsurNotiCreateSetById(){
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        return socialInsurNotiCreateSetFinder.getSocialInsurNotiCreateSetById(userId,cid);
    }
}
