package nts.uk.screen.at.ws.kmr.kmr001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.reservation.BentoMenuHistDto;
import nts.uk.screen.at.app.reservation.BentoMenuHistScreenQuery;
import nts.uk.screen.at.app.reservation.DateHistoryItemDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    @Path("get/{histId}")
    @POST
    public DateHistoryItemDto GetBentoMenuHist(@PathParam("histId") String histId){
        return bentoMenuHistScreenQuery.getBentoMenuHist(histId);

    }

}
