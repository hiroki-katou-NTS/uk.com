package nts.uk.ctx.at.aggregation.ws.scheduletable.outputsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.CopyScheduleTableOutputSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.DeleteScheduleTableOutputSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.RegisterScheduleTableOutputSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.ScheduleTableOutputSettingCopyCommand;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.ScheduleTableOutputSettingDeleteCommand;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.ScheduleTableOutputSettingSaveCommand;
import nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting.UpdateScheduleTableOutputSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting.ScheduleTableOutputSettingDto;
import nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting.ScheduleTableOutputSettingFinder;

/**
 * 
 * @author quytb
 *
 */
@Path("ctx/at/schedule/scheduletable")
@Produces("application/json")
public class ScheduleTableOutputSettingWS extends WebService{
	
	@Inject
	private ScheduleTableOutputSettingFinder finder;
	
	@Inject
	private RegisterScheduleTableOutputSettingCommandHandler registerScheduleTableOutputSettingCommandHandler;
	
	@Inject
	private UpdateScheduleTableOutputSettingCommandHandler updateScheduleTableOutputSettingCommandHandler;
	
	@Inject
	private DeleteScheduleTableOutputSettingCommandHandler deleteScheduleTableOutputSettingCommandHandler;
	
	@Inject
	private CopyScheduleTableOutputSettingCommandHandler copyScheduleTableOutputSettingCommandHandler;
	
	@POST
	@Path("getone/{code}")
	public ScheduleTableOutputSettingDto getScheduleTableOutputSettingByCidAndCode(@PathParam("code") String code){
		return this.finder.findByCidAndCode(code);
	}
	
	@POST
	@Path("getall")
	public List<ScheduleTableOutputSettingDto> getScheduleTableOutputSettingByCid(){
		return this.finder.findByCid();
	}	
	
	@POST
	@Path("register")
	public void registerScheduleTableOutputSetting(ScheduleTableOutputSettingSaveCommand command) {
		this.registerScheduleTableOutputSettingCommandHandler.handle(command);		
	}
	
	@POST
	@Path("update")
	public void updateScheduleTableOutputSetting(ScheduleTableOutputSettingSaveCommand command) {
		this.updateScheduleTableOutputSettingCommandHandler.handle(command);		
	}
	
	@POST
	@Path("delete")
	public void deleteScheduleTableOutputSetting(ScheduleTableOutputSettingDeleteCommand command) {
		this.deleteScheduleTableOutputSettingCommandHandler.handle(command);		
	}
	
	@POST
	@Path("copy")
	public void copyScheduleTableOutputSetting(ScheduleTableOutputSettingCopyCommand command) {
		this.copyScheduleTableOutputSettingCommandHandler.handle(command);		
	}
}
