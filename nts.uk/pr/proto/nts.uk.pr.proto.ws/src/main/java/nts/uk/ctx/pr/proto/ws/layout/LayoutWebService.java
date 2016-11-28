package nts.uk.ctx.pr.proto.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.find.item.ItemDto;
import nts.uk.ctx.pr.proto.app.find.item.ItemFinder;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutDto;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutMasterFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("pr/proto/layout")
@Produces("application/json")
public class LayoutWebService extends WebService {

	@Inject
	private CreateLayoutCommandHandler createLayoutData;
	@Inject
	private CreateLayoutHistoryCommandHandler createHistoryData;
	@Inject
	private UpdateLayoutHistoryCommandHandler updateData;
	@Inject
	private DeleteLayoutHistoryCommandHandler deleteData;
	@Inject
	private LayoutMasterFinder find;
	
	@POST
	@Path("findalllayout")
	public List<LayoutDto> getAllLayout(){
		return this.find.getAllLayout(AppContexts.user().companyCode());		
	}
	@POST
	@Path("findlayout/{stmtCode}/{startYm}")
	public LayoutDto getLayout(@PathParam("stmtCode") String stmtCode, @PathParam("startYm") int startYm){
		return this.find.getLayout(AppContexts.user().companyCode(), stmtCode, startYm).get();
	}
	
	@POST
	@Path("findlayoutwithmaxstartym")
	public List<LayoutDto> getLayoutsWithMaxStartYm(){
		return this.find.getLayoutsWithMaxStartYm(AppContexts.user().companyCode());
	}
	
	@POST
	@Path("createlayout")
	public void createLayout(CreateLayoutCommand command){
		this.createLayoutData.handle(command);
	}
	@POST
	@Path("createlayouthistory")
	public void createLayoutHistory(CreateLayoutHistoryCommand command){
		this.createHistoryData.handle(command);
	}
	@POST
	@Path("updatedata")
	public void updateData(UpdateLayoutHistoryCommand command){
		this.updateData.handle(command);
	}
	@POST
	@Path("deletedata")
	public void deleteData(DeleteLayoutHistoryCommand command){
		this.deleteData.handle(command);
	}
	
	
}
