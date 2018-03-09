package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface WorkTypeDivergenceReferenceTimeHistoryGetMemento.
 */
public interface WorkTypeDivergenceReferenceTimeHistoryGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	WorkTypeCode getWorkTypeCode();

	/**
	 * Gets the history items.
	 *
	 * @return the history items
	 */
	List<DateHistoryItem> getHistoryItems();
}
