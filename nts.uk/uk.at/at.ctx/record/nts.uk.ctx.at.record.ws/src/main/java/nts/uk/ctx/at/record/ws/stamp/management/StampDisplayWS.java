package nts.uk.ctx.at.record.ws.stamp.management;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampPageLayoutCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampSettingPersonCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampSettingPersonCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.DeleteStampSettingCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.DeleteStampSettingCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.UpdateStampPageLayoutCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.UpdateStampSettingPersonCommandHandler;
import nts.uk.ctx.at.record.app.find.stamp.management.StamDisplayFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.app.find.stamp.management.StampSettingPersonDto;

/**
 * 
 * @author phongtq
 *
 */
@Path("at/record/stamp/management")
@Produces("application/json")
public class StampDisplayWS extends WebService {
	
	@Inject
	private StamDisplayFinder finder; 
	
	@Inject
	private AddStampSettingPersonCommandHandler addSetPerHandler;
	
	@Inject
	private UpdateStampSettingPersonCommandHandler updateSetPerHandler;
	
	@Inject
	private AddStampPageLayoutCommandHandler  addStampPageHandler;
	
	@Inject
	private UpdateStampPageLayoutCommandHandler  updateStampPageHandler;
	
	@Inject
	private DeleteStampSettingCommandHandler removeHandler;
	
	@POST
	@Path("getStampSetting")
	public StampSettingPersonDto getStampSetting() {
		return this.finder.getStampSet();
	}
	
	@POST
	@Path("saveStampSetting")
	public void save(AddStampSettingPersonCommand command) {
		this.addSetPerHandler.handle(command);
	}
	
	@POST
	@Path("updateStampSetting")
	public void update(AddStampSettingPersonCommand command) {
		this.updateSetPerHandler.handle(command);
	}
	
	@POST
	@Path("getStampPage/{pageNo}/{buttonLayoutType}")
	public StampPageLayoutDto getStampPage(@PathParam("pageNo") int pageNo, @PathParam("buttonLayoutType") int buttonLayoutType) {
		return this.finder.getStampPage(pageNo, buttonLayoutType);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteStampSettingCommand command) {
		this.removeHandler.handle(command);
	}
	
	@POST
	@Path("getStampPageByCid")
	public List<StampPageLayoutDto> getStampPageByCid() {
		return this.finder.getAllStampPage();
	}
	
	@POST
	@Path("saveStampPage")
	public void saveStampPage(StampPageLayoutCommand command) {
		this.addStampPageHandler.handle(command);
	}
	
	@POST
	@Path("updateStampPage")
	public void updateStampPage(StampPageLayoutCommand command) {
		this.updateStampPageHandler.handle(command);
	}
}
