package nts.uk.screen.at.app.dailyperformance.correction.errorreference;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ErrorReferenceProcessor {
	@Inject
	private ErrorReferenceRepository errorReferenceRepository;
	
	public List<ErrorReferenceDto> getErrorReferences(ErrorReferenceParams params ){
		return this.errorReferenceRepository.getErrorReferences(params,AppContexts.user().companyId());
	}
}
