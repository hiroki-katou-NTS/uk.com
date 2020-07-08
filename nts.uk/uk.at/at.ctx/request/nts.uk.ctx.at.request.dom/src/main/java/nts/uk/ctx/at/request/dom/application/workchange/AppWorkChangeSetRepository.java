package nts.uk.ctx.at.request.dom.application.workchange;
/**
 * 
 * @author hoangnd
 *
 */

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;

public interface AppWorkChangeSetRepository {
	
	Optional<AppWorkChangeSet> findByCompanyId(String companyId);

	void add(AppWorkChangeSet domain);

	void update(AppWorkChangeSet domain);
	
	void remove(String companyId);
}
