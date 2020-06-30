package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.Optional;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppWorkChangeRepository {
	
	public Optional<AppWorkChange> findbyID(String appID);
	
	public void add(AppWorkChange appWorkChange);
	
	public void update(AppWorkChange appWorkChange);
	
	public void remove(AppWorkChange appWorkChange);

}
