package nts.uk.screen.com.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import companyinfor.Cmm001UpdateCommand;
import companyinfor.Cmm001UpdateCommandHandler;
import nts.arc.layer.ws.WebService;
/**
 * 
 * @author yennth
 *
 */
@Path("screen/com/cmm001")
@Produces("application/json")
public class Cmm001Ws extends WebService{
	@Inject
	private Cmm001UpdateCommandHandler update;
	
	/**
	 * update cmm001
	 * @param cm
	 */
	@POST
	@Path("update")
	public void update(Cmm001UpdateCommand cm){
		this.update.handle(cm);
	}
}
