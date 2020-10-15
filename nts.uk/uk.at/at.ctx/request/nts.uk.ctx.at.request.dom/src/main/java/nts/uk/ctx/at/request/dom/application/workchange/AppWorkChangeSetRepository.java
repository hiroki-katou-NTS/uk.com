package nts.uk.ctx.at.request.dom.application.workchange;
/**
 * 
 * @author hoangnd
 *
 */

import java.util.Optional;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

public interface AppWorkChangeSetRepository {
	
	Optional<AppWorkChangeSet> findByCompanyId(String companyId);

	void add(AppWorkChangeSet domain, int workTimeReflectAtr);

	void update(AppWorkChangeSet domain, int workTimeReflectAtr);
	
	void remove(String companyId);
	
	Optional<ReflectWorkChangeApp> findByCompanyIdReflect(String companyId);
}
