package nts.uk.screen.at.ws.krm.krm001.b;

import lombok.val;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoMenuHistCommandHandler;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.screen.at.app.reservation.BentoMenuHistDto;
import nts.uk.screen.at.app.reservation.BentoMenuHistScreenQuery;
import nts.uk.screen.at.app.reservation.BentoReservationSettingDto;
import nts.uk.screen.at.app.reservation.DateHistoryItemDto;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Optional;

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
