package nts.uk.screen.at.ws.krm.krm001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.reservation.BentoReservationSetScreenProcessor;
import nts.uk.screen.at.app.reservation.BentoReservationSettingDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("screen/at/record/reservation//bento_reservation_setting")
@Produces("application/json")
public class BentoReservationSettingWebService extends WebService {

    @Inject
    private BentoReservationSetScreenProcessor bentoReservationSetScreenProcessor;

    @GET
    @Path("get/{companyId}")
    public Optional<BentoReservationSettingDto> getReservation(@PathParam("companyId") String companyId) {
        return this.bentoReservationSetScreenProcessor.findDataBentoReservationSetting(companyId);
    }

}
