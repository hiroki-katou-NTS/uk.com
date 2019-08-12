package nts.uk.ctx.pr.report.ws.printconfig.socinsurnoticreset;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset.SocialInsurNotiCreateSetDto;
import nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset.SocialInsurNotiCreateSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;




@Path("ctx/pr/report/printdata/guaranbytheinsur")
@Produces("application/json")
public class GuaranByTheInsurWebService {

    @Inject
    private SocialInsurNotiCreateSetFinder mSocialInsurNotiCreateSetFinder;



    @POST
    @Path("/start")
    public SocialInsurNotiCreateSetDto startScreen(GeneralDate targetDate) {
        // ドメインモデル「法定調書用会社」をすべて取得する
        return mSocialInsurNotiCreateSetFinder.initScreen(targetDate);
    }


}