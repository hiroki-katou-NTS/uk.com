package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.GetDataHistoryCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.GetSaveSetHistoryCommand;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SaveSetDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.DataHistoryDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.DataHistoryFinder;
import nts.uk.ctx.sys.assist.app.find.datarestoration.DataRecoveryResultFinder;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class DataRecoveryHistoryWebService {
	@Inject
	private DataRecoveryResultFinder saveSetFinder;
	
	@Inject
	private DataHistoryFinder dataHistoryFinder;
	
	@POST
	@Path("findSaveSet")
	public List<SaveSetDto> findSaveSetHistory(GetSaveSetHistoryCommand command) {
		return saveSetFinder.getDataRecoveryResultByStartDatetime(command.getFrom(), command.getTo());
	}
	
	@POST
	@Path("findData")
	public List<DataHistoryDto> findData(GetDataHistoryCommand command) {
		return dataHistoryFinder.findData(command);
	}
}
