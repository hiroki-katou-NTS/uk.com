package nts.uk.ctx.pr.report.ws.printconfig.socinsurnoticreset;

import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.NotifiOfInsurQuaAcDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.SocialInsurNotiCreateSetDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.SocialInsurNotiCreateSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("ctx/pr/report/printconfig/socinsurnoticreset")
@Produces("application/json")
public class SocInsurNotiCreSetWebservice {

    @Inject
    private SocialInsurNotiCreateSetFinder socialInsurNotiCreateSetFinder;

    @POST
    @Path("/getSocialInsurNotiCreateSet")
    public SocialInsurNotiCreateSetDto getEmpNameChangeNotiInfor() {
        return socialInsurNotiCreateSetFinder.getSocInsurNotiCreSet();
    }

    @POST
    @Path("/loadingscreen001")
    public NotifiOfInsurQuaAcDto getDataLoadingScreenQsi001() {
        return socialInsurNotiCreateSetFinder.initScreen(null);
    }
}