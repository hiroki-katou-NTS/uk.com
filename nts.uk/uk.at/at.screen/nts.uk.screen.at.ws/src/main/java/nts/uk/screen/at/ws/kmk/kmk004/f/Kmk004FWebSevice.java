package nts.uk.screen.at.ws.kmk.kmk004.f;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsHandler;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004FWebSevice extends WebService{

	@Inject
	private UpdateSettingsForCompany updateByCom;
	
	@Inject
	private DeleteSettingsForCompany deleteByCom;
	
	@POST
	@Path("viewf/com/setting/update")
	public void addOrUpdateCom(UpdateSettingsHandler command) {
		this.updateByCom.handle(command);;
	}
	
	@POST
	@Path("viewf/com/setting/delete")
	public void DeleteCom() {
		this.deleteByCom.delete();;
	}
}
