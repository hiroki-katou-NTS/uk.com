/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class ClosureHistorry.
 */
// 締め変更履歴
@Getter
public class ClosureHistory extends DomainObject{
	
	/** The close name. */
	// 名称: 締め名称
	private CloseName closeName;
	
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The closure id. */
	// 締めＩＤ
	private ClosureId closureId;
	
	/** The closure history id. */
	private ClosureHistoryId closureHistoryId;
	
	/** The closure year. */
	// 終了年月: 年月
	@Setter
	private YearMonth endDate;
	
	/** The closure date. */
	// 締め日: 日付
	private ClosureDate closureDate;
	
	/** The start date. */
	// 開始年月: 年月
	private YearMonth startDate;

	
	/**
	 * Instantiates a new closure history.
	 *
	 * @param memento the memento
	 */
	public ClosureHistory(ClosureHistoryGetMemento memento){
		this.closeName = memento.getCloseName();
		this.closureId = memento.getClosureId();
		this.companyId = memento.getCompanyId();
		this.closureHistoryId = memento.getClosureHistoryId();
		this.endDate = memento.getEndDate();
		this.closureDate = memento.getClosureDate();
		this.startDate = memento.getStartDate();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ClosureHistorySetMemento memento){
		memento.setCloseName(this.closeName);
		memento.setClosureId(this.closureId);
		memento.setCompanyId(this.companyId);
		memento.setClosureHistoryId(this.closureHistoryId);
		memento.setEndDate(this.endDate);
		memento.setClosureDate(this.closureDate);
		memento.setStartDate(this.startDate);
	}
	
	/**
	 * To closure date.
	 *
	 * @return the int
	 */
	public int toClosureDate(){
		if(this.getClosureDate().getLastDayOfMonth()){
			return 0;
		}
		return this.getClosureDate().getDay();
	}
}
