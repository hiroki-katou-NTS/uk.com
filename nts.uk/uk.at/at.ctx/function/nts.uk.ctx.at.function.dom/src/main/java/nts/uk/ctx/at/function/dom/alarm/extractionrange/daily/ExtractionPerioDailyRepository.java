package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeAbs;

/**
 * The Interface ExtractionPerioDaily.
 */

public interface ExtractionPerioDailyRepository {

	/**find*/
	ExtractionPeriodDaily findById(ExtractionRangeAbs extractionRangeAbs);
	
	/** Add */
	void add(ExtractionPeriodDaily extractionPeriodDaily);

	/** Update */
	void update(ExtractionPeriodDaily extractionPeriodDaily);

	/** Remove */
	void remove(ExtractionRangeAbs extractionRangeAbs);
}
