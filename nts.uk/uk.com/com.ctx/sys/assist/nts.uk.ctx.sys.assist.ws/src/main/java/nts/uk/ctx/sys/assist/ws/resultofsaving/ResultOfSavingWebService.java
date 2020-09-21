package nts.uk.ctx.sys.assist.ws.resultofsaving;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.resultofsaving.ResultOfSavingCommand;
import nts.uk.ctx.sys.assist.app.command.resultofsaving.ResultOfSavingHandler;
import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.app.find.resultofsaving.ResultOfSavingDto;
import nts.uk.ctx.sys.assist.app.find.resultofsaving.ResultOfSavingFinder;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ResultOfSavingWebService extends WebService{
	@Inject
	private ResultOfSavingFinder resultOfSavingFinder;
	
	@Inject
	private ResultOfSavingHandler resultOfSavingHandler;
	
	@POST
	@Path("findResultOfSaving/{storeProcessingId}")
	public ResultOfSavingDto findResultOfSaving(@PathParam("storeProcessingId") String storeProcessingId) {
		return resultOfSavingFinder.getResultOfSavingById(storeProcessingId);
	}
	
	@POST
	@Path("updateFileSize/{storeProcessingId}/{fileId}")
	public void updateFileSize(@PathParam("storeProcessingId") String storeProcessingId, @PathParam("fileId") String fileId) {
		ResultOfSavingCommand command = new ResultOfSavingCommand(storeProcessingId, fileId);
		resultOfSavingHandler.handle(command);
	}
	
	@POST
	@Path("findResultOfSaving")
	public List<ResultOfSavingDto> findResultOfSaving(LogDataParams logDataParams) {
		return resultOfSavingFinder.getResultOfSaving(logDataParams);
	}
}
