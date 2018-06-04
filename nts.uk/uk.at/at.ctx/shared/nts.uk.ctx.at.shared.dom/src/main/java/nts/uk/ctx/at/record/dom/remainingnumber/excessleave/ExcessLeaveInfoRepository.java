package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import java.util.Optional;

public interface ExcessLeaveInfoRepository {
	
	Optional<ExcessLeaveInfo> get(String sid);
	
	void add(ExcessLeaveInfo domain);
	
	void update(ExcessLeaveInfo domain);

	void delete(String sid);
}
