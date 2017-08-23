package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Optional;

public interface GoBackDirectlyRepository {
	//find Application by ID
	public Optional<GoBackDirectly> findByApplicationID(String companyID,String appID);
}
