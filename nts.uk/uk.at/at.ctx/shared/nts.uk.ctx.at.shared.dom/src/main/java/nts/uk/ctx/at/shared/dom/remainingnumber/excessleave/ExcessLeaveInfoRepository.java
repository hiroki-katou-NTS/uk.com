package nts.uk.ctx.at.shared.dom.remainingnumber.excessleave;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExcessLeaveInfoRepository {
	
	Optional<ExcessLeaveInfo> get(String sid);
	
	List<ExcessLeaveInfo> getAll(List<String> sids,String cid);
	
	List<ExcessLeaveInfo> getAllForCPS013(List<String> sids,String cid, Map<String, Object> enums);
	
	void add(ExcessLeaveInfo domain);
	
	void addAll(List<ExcessLeaveInfo> domains);
	
	void update(ExcessLeaveInfo domain);
	
	void updateAll(List<ExcessLeaveInfo> domains);

	void delete(String sid);
}
