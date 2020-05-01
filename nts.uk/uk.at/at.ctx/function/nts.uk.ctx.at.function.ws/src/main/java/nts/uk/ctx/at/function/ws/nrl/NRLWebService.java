package nts.uk.ctx.at.function.ws.nrl;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationAdapter;

@Path("at/function/nrl")
@Produces(MediaType.APPLICATION_JSON)
public class NRLWebService extends WebService {
	
	@Inject
	private ConvertTimeRecordReservationAdapter timeRecordReserv;
	
	
	@Path("create/reserv")
	@POST
	public void createReserv() {
		//ReservReceptDataImport data = new ReservReceptDataImport("11", "22", "3", "4", "5");
		//timeRecordReserv.convertData(1, "", data);
	}

}
