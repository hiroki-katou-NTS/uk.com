package nts.uk.screen.at.ws.kmr.kmr001.b;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.reservation.BentoMenuHistDto;
import nts.uk.screen.at.app.reservation.BentoMenuHistScreenQuery;

@Path("bento/bentomenuhist")
@Produces("application/json")
public class BentoMenuHistScreenWebService extends WebService {

    @Inject
    private BentoMenuHistScreenQuery bentoMenuHistScreenQuery;

    @Path("get/all")
    @POST
    public BentoMenuHistDto GetAll() {

         return   bentoMenuHistScreenQuery.getListBentoMenuHist();

    }
}
