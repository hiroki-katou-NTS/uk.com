package nts.uk.nrl.legacy;

import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.nrl.Command;
import nts.uk.nrl.data.RequestData;
import nts.uk.nrl.request.RequestDispatcher;
import nts.uk.nrl.response.NRLResponse;
import nts.uk.nrl.xml.Frame;

@Path("/nr/old")
@Produces("application/xml; charset=shift_jis")
public class NRLWebService extends RequestDispatcher {
	
	@POST
	@Path("dataCollect.aspx")
	@RequestData({ Command.TEST, Command.POLLING, Command.SESSION, Command.ALL_IO_TIME })
	public Frame requestTimeInOut(InputStream is) {
//		NRLResponse response = ignite(is);
		NRLResponse response = NRLResponse.mute();
		return response.getEntity(Frame.class);
	}
	
	@POST
	@Path("sinseiCollect.aspx")
	public void requestApplications() {
		
	}
	
	@POST
	@Path("yoyakuCollect.aspx")
	public void requestReservations() {
		
	}
	
	@POST
	@Path("masterCollect.aspx")
	public void requestMasterDatas() {
		
	}
}
