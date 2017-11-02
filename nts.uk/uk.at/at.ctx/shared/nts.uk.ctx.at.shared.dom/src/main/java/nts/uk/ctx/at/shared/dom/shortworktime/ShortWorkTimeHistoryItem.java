/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ShortWorkTimeHistoryItem.
 * 短時間勤務履歴項目
 */
@Getter
public class ShortWorkTimeHistoryItem extends AggregateRoot {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The history id. */
	// 履歴ID
	private String historyId;
	
	/** The child care atr. */
	// 育児介護区分
	private ChildCareAtr childCareAtr;
	
	/** The lst time zone. */
	// 時間帯
	private List<SChildCareFrame> lstTimeZone;
	
	/**
	 * Instantiates a new short work time history item.
	 *
	 * @param memento the memento
	 */
	public ShortWorkTimeHistoryItem(SWorkTimeHistItemSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setEmployeeId(this.employeeId);
		memento.setChildCareAtr(this.childCareAtr);
		memento.setLstTimeZone(this.lstTimeZone);
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SWorkTimeHistItemGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.employeeId = memento.getEmployeeId();
		this.childCareAtr = memento.getChildCareAtr();
		this.lstTimeZone = memento.getLstTimeZone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childCareAtr == null) ? 0 : childCareAtr.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((lstTimeZone == null) ? 0 : lstTimeZone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortWorkTimeHistoryItem other = (ShortWorkTimeHistoryItem) obj;
		if (childCareAtr != other.childCareAtr)
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		if (lstTimeZone == null) {
			if (other.lstTimeZone != null)
				return false;
		} else if (!lstTimeZone.equals(other.lstTimeZone))
			return false;
		return true;
	}
	
}
