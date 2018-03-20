package nts.uk.ctx.at.request.infra.repository.application.appabsence;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
@Stateless
public class JpaAppAbsenceRepository implements AppAbsenceRepository{

	@Override
	public Optional<AppAbsence> getAbsenceById(String companyID, String appId) {
		// TODO Auto-generated method stub
		return null;
	}

}
