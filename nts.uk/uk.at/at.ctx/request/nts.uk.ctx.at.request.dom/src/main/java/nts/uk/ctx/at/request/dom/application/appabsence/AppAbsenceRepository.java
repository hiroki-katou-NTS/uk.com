package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.Optional;

public interface AppAbsenceRepository {

	public Optional<AppAbsence> getAbsenceById(String companyID, String appId);
}
