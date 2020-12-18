package nts.uk.screen.at.ws.kmk.kmk004.f;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteByIdHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForEmp;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForSha;
import nts.uk.screen.at.app.command.kmk.kmk004.f.DeleteSettingsForWkp;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsByIdHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForCompany;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForEmp;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForSha;
import nts.uk.screen.at.app.command.kmk.kmk004.f.UpdateSettingsForWkp;
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
	private UpdateSettingsForWkp updateByWkp;
	
	@Inject
	private DeleteSettingsForWkp deleteByWkp;
	
	@Inject
	private UpdateSettingsForEmp updateByEmp;
	
	@Inject
	private DeleteSettingsForEmp deleteByEmp;
	
	@Inject
	private UpdateSettingsForSha updateBySha;
	
	@Inject
	private DeleteSettingsForSha deleteBySha;
	
	@POST
	@Path("viewf/com/setting/update")
	public void addOrUpdateCom(UpdateSettingsHandler command) {
		this.updateByCom.handle(command);;
	}
	
	@POST
	@Path("viewf/com/setting/delete")
	public void deleteCom() {
		this.deleteByCom.delete();;
	}
	
	@POST
	@Path("viewf/wkp/setting/update")
	public void addOrUpdateWkp(UpdateSettingsByIdHandler command) {
		this.updateByWkp.handle(command);;
	}
	
	@POST
	@Path("viewf/wkp/setting/delete")
	public void deleteWkp(DeleteByIdHandler command) {
		this.deleteByWkp.handle(command);
	}
	
	@POST
	@Path("viewf/emp/setting/update")
	public void addOrUpdateEmp(UpdateSettingsByIdHandler command) {
		this.updateByEmp.handle(command);;
	}
	
	@POST
	@Path("viewf/emp/setting/delete")
	public void deleteEmp(DeleteByIdHandler command) {
		this.deleteByEmp.handle(command);;
	}
	
	@POST
	@Path("viewf/sha/setting/update")
	public void addOrUpdateSha(UpdateSettingsByIdHandler command) {
		this.updateBySha.handle(command);;
	}
	
	@POST
	@Path("viewf/sha/setting/delete")
	public void deleteSha(DeleteByIdHandler command) {
		this.deleteBySha.handle(command);;
	}
}
