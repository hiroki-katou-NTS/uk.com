package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.List;

public interface StampCardRepository {
	// Get List Card by Person ID
	List<StampCardItem> findByPersonID(String companyId,String PID);
}
