package nts.uk.screen.at.ws.krm.krm001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoMenuHistCommandHandler;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.screen.at.app.reservation.BentoMenuHistScreenQuery;
import nts.uk.screen.at.app.reservation.BentoReservationSettingDto;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("bento/bentomenuhist")
@Produces("application/json")
public class BentoMenuHistScreenWebService extends WebService {
    @Inject
    private BentoMenuHistScreenQuery bentoMenuHistScreenQuery;

    @Path("/finall")
    @GET
    public Optional<BentoMenuHistory> GetAll(){
       return bentoMenuHistScreenQuery.getListBentoMenuHist();

        }
    @Path("/get/{histId}")
    @GET
    public Optional<DateHistoryItem> GetBentoMenuHist(String histId){
        return bentoMenuHistScreenQuery.getBentoMenuHist(histId);

    }

}
