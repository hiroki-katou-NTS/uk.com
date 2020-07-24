package nts.uk.screen.at.ws.ksu001;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksu001.start.StartKSU001;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Dto;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Param;

/**
 * 
 * @author laitv
 *
 */
@Path("screen/at/schedule")
@Produces("application/json")
public class KSU001WebService extends WebService{

	@Inject
	private StartKSU001 startKSU001;
	
	@POST
	@Path("start")
	public StartKSU001Dto getDataStartScreen(StartKSU001Param param){
		StartKSU001Dto data = startKSU001.getDataStartScreen(param);
		return data;
	}
	

	
}
