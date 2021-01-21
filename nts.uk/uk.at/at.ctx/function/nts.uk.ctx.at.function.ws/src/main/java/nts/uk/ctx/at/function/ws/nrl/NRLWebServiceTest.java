package nts.uk.ctx.at.function.ws.nrl;

import java.util.List;
import java.util.Optional;

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
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendOvertimeNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendPerInfoNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReasonApplicationImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReservationMenuImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeRecordSettingImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTimeNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTypeNameImport;

@Path("at/function/nrl")
@Produces(MediaType.APPLICATION_JSON)
public class NRLWebServiceTest extends WebService {

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
		 
		 Optional<SendOvertimeNameImport> sendOvertime = sendNRDataAdapter.sendOvertime(1111, "000000000000");
		 
		 List<SendPerInfoNameImport> sendPerInfo = sendNRDataAdapter.sendPerInfo(1111, "000000000000");
		 
		 List<SendReasonApplicationImport> sendReasonApp = sendNRDataAdapter.sendReasonApp(1111, "000000000000");
		 
		 List<SendReservationMenuImport> sendReservMenu = sendNRDataAdapter.sendReservMenu(1111, "000000000000");
		 
		 Optional<SendTimeRecordSettingImport> sendTimeRecordSetting = sendNRDataAdapter.sendTimeRecordSetting(1111, "000000000000");
		 
		 List<SendWorkTimeNameImport> sendWorkTime = sendNRDataAdapter.sendWorkTime(1111, "000000000000");
		 
		 List<SendWorkTypeNameImport> sendWorkType = sendNRDataAdapter.sendWorkType(1111, "000000000000");
		 
		 return;
		 
	}

}
