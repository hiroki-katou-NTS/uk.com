package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

public interface AppOverTimeRepository {
	
	public Optional<AppOverTime> find(String companyId, String appId);
	
	public void add(AppOverTime appOverTime);
	
	public void update(AppOverTime appOverTime);
	
}
