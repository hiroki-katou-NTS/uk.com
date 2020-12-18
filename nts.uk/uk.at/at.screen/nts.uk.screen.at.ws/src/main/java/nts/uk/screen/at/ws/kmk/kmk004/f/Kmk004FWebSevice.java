package nts.uk.screen.at.ws.kmk.kmk004.f;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteByIdHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForEmp;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsByIdHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForEmp;
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
	
	@Inject
	private UpdateSettingsForEmp updateByEmp;
	
	@Inject
	private DeleteSettingsForEmp deleteByEmp;
	
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
	
	@POST
	@Path("viewf/emp/setting/update")
	public void addOrUpdateEmp(UpdateSettingsByIdHandler command) {
		this.updateByEmp.handle(command);;
	}
	
	@POST
	@Path("viewf/emp/setting/delete")
	public void DeleteEmp(DeleteByIdHandler command) {
		this.deleteByEmp.handle(command);;
	}
}
