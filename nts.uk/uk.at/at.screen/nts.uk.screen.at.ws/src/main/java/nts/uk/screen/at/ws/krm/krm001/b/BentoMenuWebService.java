package nts.uk.screen.at.ws.krm.krm001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.reservation.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/record/reservation/bento_menu")
@Produces(MediaType.APPLICATION_JSON)
public class BentoMenuWebService extends WebService{

    @Inject
    private BentoMenuSetScreenProcessor bentoMenuSetScreenProcessor;

    @POST
    @Path("getBentoMenu")
    public BentoMenuJoinBentoSettingDto getReservation() {
        return this.bentoMenuSetScreenProcessor.findDataBentoMenu();
    }

    @POST
    @Path("getBentoMenuByHist")
    public List<BentoJoinReservationSetting> getBentoMenu(BentoRequest request) {
        return this.bentoMenuSetScreenProcessor.getBentoMenuByHist(request);
    }
}
