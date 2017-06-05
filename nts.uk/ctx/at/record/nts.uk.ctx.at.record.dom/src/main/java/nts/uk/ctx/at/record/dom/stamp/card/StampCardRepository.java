package nts.uk.ctx.at.record.dom.stamp.card;

import java.util.Optional;

public interface StampCardRepository {
	// Get List Card by Person ID
	Optional<StampCardItem> findByPersonID(String PID);
}
