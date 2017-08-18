package nts.uk.ctx.at.request.dom.application.gobackdirectly.repository;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyItem;

public interface GoBackDirectlyRepository {
	//find Application by ID
	public Optional<GoBackDirectlyItem> findByApplicationID(String companyID,String appID);
}
