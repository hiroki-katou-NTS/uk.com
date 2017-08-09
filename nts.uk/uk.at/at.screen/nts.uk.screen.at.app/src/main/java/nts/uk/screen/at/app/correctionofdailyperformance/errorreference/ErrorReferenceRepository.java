package nts.uk.screen.at.app.correctionofdailyperformance.errorreference;

import java.util.List;

public interface ErrorReferenceRepository {
	 List<ErrorReferenceDto> getErrorReferences(ErrorReferenceParams params , String companyId);
}
