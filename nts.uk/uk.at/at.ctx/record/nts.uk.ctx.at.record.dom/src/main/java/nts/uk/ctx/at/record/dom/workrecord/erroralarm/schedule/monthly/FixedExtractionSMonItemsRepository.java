package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import java.util.List;

public interface FixedExtractionSMonItemsRepository {
	/**
	 * Get all data schedule fix item month
	 * @return
	 */
    List<FixedExtractionSMonItems> getAll();
}
