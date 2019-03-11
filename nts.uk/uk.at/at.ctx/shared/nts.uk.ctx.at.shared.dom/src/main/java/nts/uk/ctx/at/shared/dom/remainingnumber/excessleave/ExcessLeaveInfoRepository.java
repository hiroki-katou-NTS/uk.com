package nts.uk.ctx.at.shared.dom.remainingnumber.excessleave;

import java.util.List;
import java.util.Optional;

public interface ExcessLeaveInfoRepository {
	
	Optional<ExcessLeaveInfo> get(String sid);
	List<ExcessLeaveInfo> getAll(List<String> sids,String cid);
	
	void add(ExcessLeaveInfo domain);
	
	void update(ExcessLeaveInfo domain);

	void delete(String sid);
}
