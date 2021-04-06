package nts.uk.ctx.at.record.ws.kdp.kdp010.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.TimeStampInputSettingsCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.application.AddStamFunctionCommand;
import nts.uk.ctx.at.record.app.command.stamp.application.AddStamFunctionCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.application.AddStamPromptApplicationCommad;
import nts.uk.ctx.at.record.app.command.stamp.application.AddStamPromptApplicationCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.application.DeleteStamFunctionCommand;
import nts.uk.ctx.at.record.app.command.stamp.application.DeleteStamFunctionCommandHandler;
import nts.uk.ctx.at.record.app.find.stamp.application.StamPromptAppFinder;
import nts.uk.ctx.at.record.app.find.stamp.application.StampFunctionDto;
import nts.uk.ctx.at.record.app.find.stamp.application.StampFunctionFinder;
import nts.uk.ctx.at.record.app.find.stamp.application.StampPronptAppDto;
import nts.uk.ctx.at.record.app.find.stamp.management.NoticeSetAndAupUseArtDto;
import nts.uk.ctx.at.record.app.find.stamp.management.NoticeSetFinder;

@Path("at/record/stamp/application")
@Produces("application/json")
public class StampFunctionWS extends WebService{
	
	@Inject 
	private StamPromptAppFinder finder;
	@Inject
	private AddStamPromptApplicationCommandHandler handler;
	
	@Inject 
	private StampFunctionFinder functionFinder;
	@Inject
	private AddStamFunctionCommandHandler functionCommandHandler;
	@Inject
	private DeleteStamFunctionCommandHandler delHandler;
	
	@Inject
	private NoticeSetFinder noticeSetFinder;
	
	@Inject
	private TimeStampInputSettingsCommandHandler noticeSetCommandHandler;
	
	
	@POST
	@Path("getStampApp")
	public List<StampPronptAppDto> getStampApp() {
		return this.finder.getStampSet();
	}
	
	@POST
	@Path("saveStampApp")
	public void saveStampApp(AddStamPromptApplicationCommad command) {
		this.handler.handle(command);
	}
	
	@POST
	@Path("getStampFunc")
	public StampFunctionDto getStampFunc() {
		return this.functionFinder.getStampSet();
	}
	
	@POST
	@Path("saveStampFunc")
	public void saveStampFunc(AddStamFunctionCommand command) {
		this.functionCommandHandler.handle(command);
	}
	
	@POST
	@Path("getNoticeSetAndAupUseArt")
	public NoticeSetAndAupUseArtDto getNoticeSetAndAupUseArt() {
		return this.noticeSetFinder.getNoticeSetAndAupUseArt();
	}
	
	@POST
	@Path("saveNoticeSetAndAupUseArt")
	public void saveNoticeSetAndAupUseArt(NoticeSetAndAupUseArtDto command) {
		this.noticeSetCommandHandler.saveNoticeSetAndAupUseArt(command);
	}
	
	@POST
	@Path("delete")
	public void deleteStampFunc(DeleteStamFunctionCommand command) {
		this.delHandler.handle(command);
	}
}

