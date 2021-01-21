package nts.uk.ctx.sys.assist.ws.deletiondata;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.GetDataHistoryCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.GetSaveSetHistoryCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteSetDto;
import nts.uk.ctx.sys.assist.app.find.deletedata.DeleteDelDataHandler;
import nts.uk.ctx.sys.assist.app.find.deletedata.DeletionHistoryDto;
import nts.uk.ctx.sys.assist.app.find.deletedata.DeletionHistoryFinder;
import nts.uk.ctx.sys.assist.app.find.deletedata.DeletionSaveSetFinder;

@Path("ctx/sys/assist/deletedata")
@Produces("application/json")
public class DelDataHistoryWebService {
	
	@Inject
	private DeletionSaveSetFinder deletionSetFinder;
	
	@Inject
	private DeletionHistoryFinder deletionHistoryFinder;
	
	@Inject
	private DeleteDelDataHandler deleteDelDataHandler;
	
	@POST
	@Path("findSaveSet")
	public List<DeleteSetDto> findSaveSetHistory(GetSaveSetHistoryCommand command) {
		return deletionSetFinder.findSaveSet(command.getFrom(), command.getTo());
	}
	
	@POST
	@Path("findData")
	public List<DeletionHistoryDto> findData(GetDataHistoryCommand command) {	
		return deletionHistoryFinder.findHistory(command);
	}
	
	@POST
	@Path("deleteData")
	public void deleteData(String fileId) {
		deleteDelDataHandler.handle(fileId);
	}
}
