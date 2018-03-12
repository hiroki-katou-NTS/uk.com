package nts.uk.ctx.at.request.ws.application.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.DeleteStampRequestSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.RemoveStampRequestSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.StampRequestSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.UpdateStampRequestSettingCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.StampRequestSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;

/**
 * 
 * @author yennth
 *
 */
@Path("at/request/application/stamprequest")
@Produces("application/json")
public class StampRequestSettingWebService extends WebService{
	@Inject
	private StampRequestSettingFinder finder;
	
	@Inject
	private UpdateStampRequestSettingCommandHandler update;
	
	@Inject
	private DeleteStampRequestSettingCommandHandler delete;
	
	/**
	 * Doan Duy Hung
	 * @return
	 */
	@POST
	@Path("findByComID")
	public StampRequestSettingDto findByID(){
		return this.finder.findByCompanyID();
	}
	
	/**
	 * TanLV
	 * @return
	 */
	@POST
	@Path("updateStamp")
	public void updateStamp(StampRequestSettingCommand command){
		this.update.handle(command);
	}
	
	/**
	 * TanLV
	 * @return
	 */
	@Path("deleteStamp")
	@POST
	public void deleteStamp(RemoveStampRequestSettingCommand command) { 
		this.delete.handle(command);
	}
}
