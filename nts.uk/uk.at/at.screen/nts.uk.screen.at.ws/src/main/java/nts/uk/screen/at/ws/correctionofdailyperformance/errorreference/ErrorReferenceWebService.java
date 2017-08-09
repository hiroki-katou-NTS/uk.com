package nts.uk.screen.at.ws.correctionofdailyperformance.errorreference;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceDto;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceParams;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceProcessor;
@Path("screen/at/correctionofdailyperformance/errorreference")
@Produces("application/json")
public class ErrorReferenceWebService extends WebService {
	
		@Inject
		private ErrorReferenceProcessor errorReferenceProcessor;

		@POST
		@Path("getErrorReferences")
		public List<ErrorReferenceDto> getErrorReferences(ErrorReferenceParams params ){
			return this.errorReferenceProcessor.getErrorReferences(params);
		}

}
