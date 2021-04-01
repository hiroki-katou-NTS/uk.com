package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AppOverTimeRepository {
	
	public Optional<AppOverTime> find(String companyId, String appId);
	
	public void add(AppOverTime appOverTime);
	
	public void update(AppOverTime appOverTime);
	
	public void remove(String companyID, String appID);
	
	public List<AppOverTime> getByListAppId(String companyId, List<String> appIds);
	
	public Map<String, AppOverTime> getHashMapByID(String companyId, List<String> appIds);
}
