package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;

public interface DivergenceReasonSelectRepository {

	List<DivergenceReasonSelect> findAllReason(int divTimeId);
	
	void delete(DivergenceReasonSelect divergenceReasonSelect);
	void add(DivergenceReasonSelect divergenceReasonSelect);
}
