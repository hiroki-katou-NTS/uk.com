package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.app.find.resultofrestoration.ResultOfRestorationDto;
import nts.uk.ctx.sys.assist.app.find.resultofrestoration.ResultOfRestorationFinder;


@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ResultOfRestorationWebService {

	@Inject
	private ResultOfRestorationFinder resultOfRestorationFinder;
	
	@POST
	@Path("findResultOfRestoration")
	public List<ResultOfRestorationDto> findResultOfRestoration(LogDataParams logDataParams) {
		return resultOfRestorationFinder.getResultOfRestoration(logDataParams);
	}
}
