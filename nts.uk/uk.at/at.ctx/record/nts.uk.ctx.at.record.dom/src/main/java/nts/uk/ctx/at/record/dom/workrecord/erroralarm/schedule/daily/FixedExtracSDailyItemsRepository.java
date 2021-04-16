package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import java.util.List;

public interface FixedExtracSDailyItemsRepository {
	/**
	 * Get data default schedule fix item day
	 * @return list schedule fix item day
	 */
    List<FixedExtractionSDailyItems> getAll();
}
