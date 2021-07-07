package nts.uk.screen.at.ws.knr.knr002.h;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.h.RegistDto;
import nts.uk.screen.at.app.query.knr.knr002.h.RegistSpecifiedEmployeeOnScreen;

/**
*
* @author xuannt
*
*/
@Path("screen/at/employeestransfer")
@Produces(MediaType.APPLICATION_JSON)
public class RegistSpecifiedEmployeeOnScreenWS {
	@Inject
	private RegistSpecifiedEmployeeOnScreen registSpecifiedEmps;
	@POST
	@Path("registSpecifiedEmps")
	public void registSpecifiedEmps(RegistDto dto) {
		this.registSpecifiedEmps.registSpecifiedEmployeeOnScreen(dto.getTerminalCode(), dto.getSelectedEmpIds());
	}
	
}
