//package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.fixedverticalsetting;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//
//import nts.arc.layer.app.command.JavaTypeResult;
//import nts.arc.layer.ws.WebService;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddFixedVerticalSettingCommand;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddFixedVerticalSettingCommandHandler;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalTimeSettingCommand;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalTimeSettingCommandHandler;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.DeleteVerticalTimeSettingCommand;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.DeleteVerticalTimeSettingCommandHandler;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.UpdateFixedVerticalSettingCommandHandler;
//import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.UpdateVerticalTimeSettingCommandHandler;
//import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingDto;
//import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingFinder;
//import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalTimeSettingDto;
//import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalTimeSettingFinder;
///**
// * 
// * @author phongtq
// *
// */
//@Path("ctx/at/schedule/budget/fixedverticalsetting")
//@Produces("application/json")
//public class FixedVerticalSettingWS extends WebService{
//	@Inject
//	private FixedVerticalSettingFinder verticalSettingFinder;
//	
//	@Inject
//	private VerticalTimeSettingFinder verticalTimeSettingFinder;
//	
//	@Inject
//	private AddFixedVerticalSettingCommandHandler addVerticalSettingCommandHandler;
//	
//	@Inject
//	private UpdateFixedVerticalSettingCommandHandler updateFixedVerticalSettingCommandHandler;
//	
//	@Inject
//	private AddVerticalTimeSettingCommandHandler addVerticalTimeSettingCommandHandler;
//	
//	@Inject
//	private UpdateVerticalTimeSettingCommandHandler updateVerticalTimeSettingCommandHandler;
//	
//	@Inject
//	private DeleteVerticalTimeSettingCommandHandler deleteVerticalTimeSettingCommandHandler; 
//	
//	@Path("findByCid")
//	@POST
//	public List<FixedVerticalSettingDto> findByCid() {
//		return verticalSettingFinder.findByCid();
//
//	}
//	
//	@Path("find/{fixedVerticalNo}")
//	@POST
//	public List<VerticalTimeSettingDto> findAll(@PathParam("fixedVerticalNo") int fixedVerticalNo) {
//		return verticalTimeSettingFinder.findAll(fixedVerticalNo);
//
//	}
//	
//	@Path("addFixedVertical")
//	@POST
//	public void add(AddFixedVerticalSettingCommand command) { 
//		this.addVerticalSettingCommandHandler.handle(command);
//	}
//	
//	@Path("updateFixedVertical")
//	@POST
//	public void update(AddFixedVerticalSettingCommand command) {
//		this.updateFixedVerticalSettingCommandHandler.handle(command);
//	}
//	
//	@Path("addVerticalTime")
//	@POST
//	public JavaTypeResult<List<String>> add(AddVerticalTimeSettingCommand command) {
//		return new JavaTypeResult<List<String>>(this.addVerticalTimeSettingCommandHandler.handle(command));
//	}
//	
//	@Path("updateVerticalTime")
//	@POST
//	public JavaTypeResult<List<String>> update(AddVerticalTimeSettingCommand command) {
//		return new JavaTypeResult<List<String>>(this.updateVerticalTimeSettingCommandHandler.handle(command));
//	}
//	
//	@Path("deleteVerticalTime")
//	@POST
//	public void deleteVerticalTime (DeleteVerticalTimeSettingCommand command){
//		deleteVerticalTimeSettingCommandHandler.handle(command);
//	}
//}
