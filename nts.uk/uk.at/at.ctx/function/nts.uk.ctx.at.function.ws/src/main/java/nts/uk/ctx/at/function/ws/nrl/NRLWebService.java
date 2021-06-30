package nts.uk.ctx.at.function.ws.nrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.RequestData;
import nts.uk.ctx.at.function.app.nrl.request.RequestDispatcher;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

@Path("/nr/process")
@Produces("application/xml; charset=shift_jis")
public class NRLWebService extends RequestDispatcher {

	@POST
	@Path("dataCollect")
	@RequestData({ Command.TEST, Command.POLLING, Command.SESSION, Command.TIMESET_INFO, Command.ALL_IO_TIME })
	public Frame requestTimeInOut(InputStream is) {
		NRLResponse response = ignite(is);
		// NRLResponse response = NRLResponse.mute();
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("sinseiCollect")
	@RequestData({ Command.ALL_PETITIONS })
	public Frame requestApplications(InputStream is) {
		NRLResponse response = ignite(is);
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("yoyakuCollect")
	@RequestData({ Command.ALL_RESERVATION })
	public Frame requestReservations(InputStream is) {
		NRLResponse response = ignite(is);
		return response.getEntity(Frame.class);
	}

	@POST
	@Path("masterCollect")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@RequestData({ Command.PERSONAL_INFO, Command.OVERTIME_INFO, Command.RESERVATION_INFO, 
			Command.WORKTIME_INFO, Command.WORKTYPE_INFO, Command.APPLICATION_INFO, Command.TR_REMOTE, Command.UK_SWITCH_MODE })
	public Response requestMasterDatas(InputStream is) {
		NRLResponse response = ignite(is);
		Frame frame = response.getEntity(Frame.class);
		return Response.ok().type(MediaType.APPLICATION_OCTET_STREAM).entity(trim(frame)).build();
	}
	
	public String trim(Frame frame) {
		StringWriter sw = new StringWriter();
		JAXB.marshal(frame, sw);
		String xmlString = sw.toString();
	    BufferedReader reader = new BufferedReader(new StringReader(xmlString));
	    StringBuffer result = new StringBuffer();
	    try {
	    	int lineNumber = 0;
	        String line;
	        while ( (line = reader.readLine() ) != null) {
	        	if(lineNumber != 0) {
	        	String row = line.trim();
	        	if(row.length() >=2 && row.substring(row.length()-2, row.length()).endsWith("/>")) {
	        		String endChar = row.substring(0, row.length()-2) + " />";
	        		 result.append(endChar);
	        	}else {
	            result.append(row);
	        	}
	            result.append("\n");
	        	}else {
	        		result.append("<?xml version=\"1.0\" encoding=\"shift_jis\" ?>");
	 	            result.append("\n");
	        		lineNumber++;
	        	}
	        }
	        return result.toString().trim();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
}
