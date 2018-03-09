package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface WorkTypeDivergenceReferenceTimeHistorySetMemento.
 */
public interface WorkTypeDivergenceReferenceTimeHistorySetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	void setWorkTypeCode(WorkTypeCode workTypeCode);

	/**
	 * Sets the history items.
	 *
	 * @param historyItems the new history items
	 */
	void setHistoryItems(List<DateHistoryItem> historyItems);
}
