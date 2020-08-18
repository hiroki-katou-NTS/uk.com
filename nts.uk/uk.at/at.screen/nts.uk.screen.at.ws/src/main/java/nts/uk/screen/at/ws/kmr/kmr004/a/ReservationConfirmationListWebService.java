package nts.uk.screen.at.ws.kmr.kmr004.a;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.reservation.ReservationConfirmationListDto;
import nts.uk.screen.at.app.reservation.ReservationConfirmationListScreenQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * @author 3si - Dang Huu Khai
 */
@Path("screen/at/record/reservation-conf-list")
@Produces("application/json")
public class ReservationConfirmationListWebService extends WebService {

   @Inject
    private ReservationConfirmationListScreenQuery reservationConfirmationListScreenQuery;

    @POST
    @Path("start/{strDate}")
    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(@FormParam("strDate") String strDate) {
        LoginUserContext user = AppContexts.user();
        // GeneralDate date = GeneralDate.fromString(strDate, "yyyy/MM/dd");
        String employeeId = user.employeeId();
        String companyId = user.companyId();

        // 予約確認一覧
        return reservationConfirmationListScreenQuery.getReservationConfirmationListStartupInfo(companyId, employeeId, GeneralDate.today());
    }
}