package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeAbs;

/**
 * The Interface ExtractionPerioDaily.
 */

public interface ExtractionPerioDailyRepository {

	/** Add */
	void add(ExtractionPeriodDaily extractionPeriodDaily);

	/** Update */
	void update(ExtractionPeriodDaily extractionPeriodDaily);

	/** Remove */
	void remove(ExtractionRangeAbs extractionRangeAbs);
}
