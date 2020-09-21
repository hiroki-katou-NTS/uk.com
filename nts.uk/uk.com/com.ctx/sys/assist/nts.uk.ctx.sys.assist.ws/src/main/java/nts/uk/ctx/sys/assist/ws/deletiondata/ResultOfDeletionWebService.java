package nts.uk.ctx.sys.assist.ws.deletiondata;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.app.find.resultofdeletion.ResultOfDeletionDto;
import nts.uk.ctx.sys.assist.app.find.resultofdeletion.ResultOfDeletionFinder;


@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ResultOfDeletionWebService {
	
	@Inject
	private ResultOfDeletionFinder resultOfDeletionFinder;

	@POST
	@Path("findResultOfRestoration")
	public List<ResultOfDeletionDto> findResultOfRestoration(LogDataParams logDataParams) {
		return resultOfDeletionFinder.getResultOfDeletion(logDataParams);
	}

}
