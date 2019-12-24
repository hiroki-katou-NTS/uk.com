package nts.uk.ctx.pereg.ws.checkdataofemployee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.pereg.app.command.process.checkdata.CheckDataFromUI;
import nts.uk.ctx.pereg.app.command.process.checkdata.ExecuteCheckDataAsynComHandle;

@Path("ctx/pereg/check")
@Produces("application/json")
public class GetDataForCheckWebservice {
	
	@Inject
	private ExecuteCheckDataAsynComHandle checkDataCommand;

	@POST
	@Path("checkdata")
	public AsyncTaskInfo CheckData(CheckDataFromUI query) {
		return this.checkDataCommand.handle(query);
	}
}
