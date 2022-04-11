package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppWorkChangeRepository {
	
	public Optional<AppWorkChange> findbyID(String companyId, String appID);
	
	public void add(AppWorkChange appWorkChange);
	
	public void update(AppWorkChange appWorkChange);
	
	public void remove(String companyID, String appID);
	
	public Optional<AppWorkChange> findbyID(String companyId, String appID, Application app);
	
}
