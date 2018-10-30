package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import java.util.List;

public interface SocialInsurancePrefectureInformationRepository {
	
	public List<SocialInsurancePrefectureInformation> findByHistory ();
	
}
