package nts.uk.ctx.at.function.ws.nrl;

import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.RequestData;
import nts.uk.ctx.at.function.app.nrl.request.RequestDispatcher;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

@Path("/nr/old")
@Produces("application/xml; charset=shift_jis")
public class NRLWebService extends RequestDispatcher {

	@POST
	@Path("dataCollect.aspx")
	@RequestData({ Command.TEST, Command.POLLING, Command.SESSION, Command.ALL_IO_TIME })
	public Frame requestTimeInOut(InputStream is) {
		NRLResponse response = ignite(is);
		// NRLResponse response = NRLResponse.mute();
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("sinseiCollect.aspx")
	@RequestData({ Command.ALL_PETITIONS })
	public Frame requestApplications(InputStream is) {
		NRLResponse response = ignite(is);
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("yoyakuCollect.aspx")
	@RequestData({ Command.ALL_RESERVATION })
	public Frame requestReservations(InputStream is) {
		NRLResponse response = ignite(is);
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("masterCollect.aspx")
	@RequestData({ Command.PERSONAL_INFO, Command.OVERTIME_INFO, Command.RESERVATION_INFO, Command.TIMESET_INFO,
			Command.WORKTIME_INFO, Command.WORKTYPE_INFO, Command.TR_REMOTE })
	public Frame requestMasterDatas(InputStream is) {
		NRLResponse response = ignite(is);
		return response.getEntity(Frame.class);
	}
}
