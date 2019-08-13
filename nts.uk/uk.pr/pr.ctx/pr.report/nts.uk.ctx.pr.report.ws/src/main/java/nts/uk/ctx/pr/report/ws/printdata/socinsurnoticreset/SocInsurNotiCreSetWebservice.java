package nts.uk.ctx.pr.report.ws.printdata.socinsurnoticreset;

import nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset.SocialInsurNotiCreateSetDto;
import nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset.SocialInsurNotiCreateSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("ctx/pr/report/printdata/socinsurnoticreset")
@Produces("application/json")
public class SocInsurNotiCreSetWebservice {

    @Inject
    private SocialInsurNotiCreateSetFinder socialInsurNotiCreateSetFinder;

    @POST
    @Path("/getSocialInsurNotiCreateSet")
    public List<SocialInsurNotiCreateSetDto> getEmpNameChangeNotiInfor() {
        return socialInsurNotiCreateSetFinder.getSocInsurNotiCreSet();
    }
}