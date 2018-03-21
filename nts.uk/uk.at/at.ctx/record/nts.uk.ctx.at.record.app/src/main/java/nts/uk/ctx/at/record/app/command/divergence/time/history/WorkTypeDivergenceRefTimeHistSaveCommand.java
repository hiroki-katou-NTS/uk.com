package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryGetMemento;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkTypeDivergenceRefTimeHistSaveCommand.
 */
@Data
public class WorkTypeDivergenceRefTimeHistSaveCommand implements WorkTypeDivergenceReferenceTimeHistoryGetMemento{
	
	/** The work type code. */
	private String workTypeCodes;
	
	/** The history id. */
	private String historyId;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The is copy data. */
	private boolean isCopyData;
	
	public WorkTypeDivergenceRefTimeHistSaveCommand() {
		super();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryGetMemento#getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.workTypeCodes);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryGetMemento#getHistoryItems()
	 */
	@Override
	public List<DateHistoryItem> getHistoryItems() {
		String histId = null;
		if(StringUtils.isEmpty(this.historyId)){
			histId = UUID.randomUUID().toString();
		} else {
			histId = this.historyId;
		}
		
		List<DateHistoryItem> list = new ArrayList<DateHistoryItem>();
		
		DatePeriod period = new DatePeriod(this.startDate, this.endDate);
		list.add(new DateHistoryItem(histId, period));
		return list;
	}
}
