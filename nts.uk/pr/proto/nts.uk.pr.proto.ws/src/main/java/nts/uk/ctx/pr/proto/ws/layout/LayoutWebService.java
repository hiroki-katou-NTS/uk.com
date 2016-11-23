package nts.uk.ctx.pr.proto.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutDto;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutMasterFinder;

@Path("/ctx/pr/proto/layout")
@Produces("application/json")
public class LayoutWebService {
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
	@Path("findalllayout/{companyCode}")
	public List<LayoutDto> getAllLayout(@PathParam("companyCode") String companyCode){
		return this.find.getAllLayout(companyCode);		
	}
	@POST
	@Path("findlayout/{companyCode, stmtCode, startYm}")
	public LayoutDto getLayout(@PathParam("companyCode") String companyCode,
			@PathParam("stmtCode") String stmtCode,
			@PathParam("startYm") int startYm){
		return this.find.getLayout(companyCode, stmtCode, startYm).get();
	}
	
	@POST
	@Path("findalllayoutwithmaxstartym/{companyCode}")
	public List<LayoutDto> getLayoutsWithMaxStartYm(@PathParam("companyCode") String companyCode){
		return this.find.getLayoutsWithMaxStartYm(companyCode);
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
