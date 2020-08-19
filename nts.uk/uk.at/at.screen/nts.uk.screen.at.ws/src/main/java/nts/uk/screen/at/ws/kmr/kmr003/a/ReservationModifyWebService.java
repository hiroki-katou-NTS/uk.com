package nts.uk.screen.at.ws.kmr.kmr003.a;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationSearchConditionDto;
import nts.uk.screen.at.app.kmr003.query.ReservationModifyDto;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.screen.at.app.kmr003.query.ReservationModifyQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/record/reservation/bento_modify")
@Produces("application/json")
public class ReservationModifyWebService extends WebService {

    @Inject
    private ReservationModifyQuery reservationModifyQuery;

    @POST
    @Path("getReservations")
    public ReservationModifyDto getReservations(ReservationModifyParam param) {
        return this.reservationModifyQuery.getReservations(param.getEmpIds(),
                new ReservationDate(param.getDate(), EnumAdaptor.valueOf(param.getClosingTimeFrame(), ReservationClosingTimeFrame.class)),
                EnumAdaptor.valueOf(param.getSearchCondition(), BentoReservationSearchConditionDto.class));
    }
}
