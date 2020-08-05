package nts.uk.screen.at.ws.krm.krm001.b;

import nts.uk.screen.at.app.reservation.BentoMenuDto;
import nts.uk.screen.at.app.reservation.BentoMenuSetScreenProcessor;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("screen/at/record/reservation//bento_menu")
@Produces("application/json")
public class BentoMenuWebService {

    @Inject
    private BentoMenuSetScreenProcessor bentoMenuSetScreenProcessor;

    @GET
    @Path("getBentoMenu")
    public Optional<BentoMenuDto> getReservation() {
        return this.bentoMenuSetScreenProcessor.findDataBentoMenu();
    }
}
