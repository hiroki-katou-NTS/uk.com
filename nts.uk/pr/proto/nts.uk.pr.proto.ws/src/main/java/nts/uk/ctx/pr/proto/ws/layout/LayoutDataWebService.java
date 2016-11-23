package nts.uk.ctx.pr.proto.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.CreateLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.DeleteLayoutHistoryCommandHandler;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommand;
import nts.uk.ctx.pr.proto.app.command.layout.UpdateLayoutHistoryCommandHandler;

@Path("pr/proto/layoutMaster")
@Produces("application/json")
public class LayoutDataWebService {
	@Inject
	private CreateLayoutCommandHandler createLayoutData;
	@Inject
	private CreateLayoutHistoryCommandHandler createHistoryData;
	@Inject
	private UpdateLayoutHistoryCommandHandler updateData;
	@Inject
	private DeleteLayoutHistoryCommandHandler deleteData;
	@POST
	@Path("createLayout")
	private void createLayout(CreateLayoutCommand command){
		this.createLayoutData.handle(command);
	}
	@POST
	@Path("createLayoutHistory")
	private void createLayoutHistory(CreateLayoutHistoryCommand command){
		this.createHistoryData.handle(command);
	}
	@POST
	@Path("updateData")
	private void updateData(UpdateLayoutHistoryCommand command){
		this.updateData.handle(command);
	}
	@POST
	@Path("deleteData")
	private void deleteData(DeleteLayoutHistoryCommand command){
		this.deleteData.handle(command);
	}
}
