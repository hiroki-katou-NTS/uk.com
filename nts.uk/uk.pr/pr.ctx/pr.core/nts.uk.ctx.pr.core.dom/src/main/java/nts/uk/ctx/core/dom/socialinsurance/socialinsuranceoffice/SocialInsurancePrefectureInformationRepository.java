package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import java.util.List;

public interface SocialInsurancePrefectureInformationRepository {
	
	public List<SocialInsurancePrefectureInformation> findByHistory ();
	
}
