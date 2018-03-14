package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryGetMemento;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class CompanyDivergenceReferenceTimeHistoryCommand.
 */
@Data
public class ComDivergenceRefTimeHistSaveCommand implements CompanyDivergenceReferenceTimeHistoryGetMemento {
	
	/** The history id. */
	private String historyId;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/**
	 * Instantiates a new company divergence reference time history command.
	 */
	public ComDivergenceRefTimeHistSaveCommand() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryGetMemento#getHistoryItems()
	 */
	@Override
	public List<DateHistoryItem> getHistoryItems() {
		String histId = null;
		if(this.historyId.isEmpty()){
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
