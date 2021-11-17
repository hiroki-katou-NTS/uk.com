package nts.uk.screen.at.ws.kmr.kmr001.b;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.query.reservation.ReservationQueryOuput;
import nts.uk.ctx.at.record.app.query.reservation.ReservationSettingQuery;
import nts.uk.screen.at.app.reservation.BentoJoinReservationSetting;
import nts.uk.screen.at.app.reservation.BentoMenuJoinBentoSettingDto;
import nts.uk.screen.at.app.reservation.BentoMenuSetScreenProcessor;
import nts.uk.screen.at.app.reservation.BentoRequest;
import nts.uk.screen.at.app.reservation.WorkLocationDto;

@Path("at/record/reservation/bento-menu")
@Produces("application/json")
public class BentoMenuWebService extends WebService{

    @Inject
    private BentoMenuSetScreenProcessor bentoMenuSetScreenProcessor;
    
    @Inject
    private ReservationSettingQuery reservationSettingQuery;

    @POST
    @Path("getbentomenu")
    public BentoMenuJoinBentoSettingDto getReservation() {
        return this.bentoMenuSetScreenProcessor.findDataBentoMenu();
    }

    @POST
    @Path("getbentomenubyhist")
    public BentoJoinReservationSetting getBentoMenu(BentoRequest request) {
        return this.bentoMenuSetScreenProcessor.getBentoMenuByHist(request);
    }

    @POST
    @Path("getworklocation")
    public List<WorkLocationDto> getWorklocation() {
        return this.bentoMenuSetScreenProcessor.getWorkLocationByContr();
    }
    
    @POST
    @Path("start")
    public ReservationQueryOuput start() {
        return reservationSettingQuery.getReservationSetting();
    }
}
