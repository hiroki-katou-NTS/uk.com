package nts.uk.screen.at.ws.krm.krm001.b;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.reservation.BentoReservationSetScreenProcessor;
import nts.uk.screen.at.app.reservation.BentoReservationSettingDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Optional;

@Path("screen/at/record/reservation//bento_reservation_setting")
@Produces("application/json")
public class BentoReservationSettingWebService extends WebService {

    @Inject
    private BentoReservationSetScreenProcessor bentoReservationSetScreenProcessor;

    @POST
    @Path("get/{companyId}")
    public BentoReservationSettingDto getReservation(@PathParam("companyId") String companyId) {
        return this.bentoReservationSetScreenProcessor.findDataBentoReservationSetting(companyId);
    }

}
