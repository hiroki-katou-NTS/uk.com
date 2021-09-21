/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.util.range.ComparableRange;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * The Class ShortWorkTimeHistoryItem.
 */
// 短時間勤務履歴項目
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
	private List<SChildCareFrame> lstTimeSlot;
	
	/**
	 * Instantiates a new short work time history item.
	 *
	 * @param memento the memento
	 */
	public ShortWorkTimeHistoryItem(SWorkTimeHistItemGetMemento memento) {
		this(memento.getHistoryId(), memento.getEmployeeId(), memento.getChildCareAtr(), memento.getLstTimeSlot());
	}
	
	public ShortWorkTimeHistoryItem(String employeeId, String historyId, ChildCareAtr childCareAtr, List<SChildCareFrame> lstTimeSlot) {
		
		// 時間帯(List)はそれぞれ重複してはいけない
		if (ComparableRange.isMutuallyExclusive(getTimeSpans(lstTimeSlot)) == false) {
			throw new RuntimeException("Msg_859");
		}
		
		this.historyId = historyId;
		this.employeeId = employeeId;
		this.childCareAtr = childCareAtr;
		this.lstTimeSlot = lstTimeSlot;
	}

	private List<TimeSpanForCalc> getTimeSpans(List<SChildCareFrame> lstTimeSlot) {
		return lstTimeSlot.stream()
				.map(e -> e.getSpan())
				.collect(toList());
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SWorkTimeHistItemSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setEmployeeId(this.employeeId);
		memento.setChildCareAtr(this.childCareAtr);
		memento.setLstTimeSlot(this.lstTimeSlot);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childCareAtr == null) ? 0 : childCareAtr.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((lstTimeSlot == null) ? 0 : lstTimeSlot.hashCode());
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
		if (lstTimeSlot == null) {
			if (other.lstTimeSlot != null)
				return false;
		} else if (!lstTimeSlot.equals(other.lstTimeSlot))
			return false;
		return true;
	}
	
}
