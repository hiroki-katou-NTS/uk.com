package nts.uk.ctx.at.function.ws.nrl;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTimeRecordStampAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ReservReceptDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;

@Path("at/function/nrl")
@Produces(MediaType.APPLICATION_JSON)
public class NRLWebService extends WebService {

	@Inject
	private ConvertTimeRecordReservationAdapter timeRecordReserv;

	@Inject
	private ConvertTimeRecordStampAdapter convertTimeRecordStampAdapter;

	@Inject
	private SendNRDataAdapter sendNRDataAdapter;

	@Path("create/reserv")
	@POST
	public void createReserv() {
		 ReservReceptDataImport data = new ReservReceptDataImport("106954", "A", "201112",
		 "131459", "5");
		 timeRecordReserv.convertData(1111, "000000000000", data);
	}

}
