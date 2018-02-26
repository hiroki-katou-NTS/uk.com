/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
// 週間休日設定
public class WeekHolidaySetting extends AggregateRoot{
	
	/** The cid. */
	// 会社ID
	private String CID;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private WeekNumberOfDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private WeekNumberOfDay outLegalHoliday;
	
	/** The start day. */
	// 開始曜日
	private DayOfWeek startDay;
	
	public WeekHolidaySetting(WeekHolidaySettingGetMemento memento) {
		this.CID = memento.getCID();
		this.inLegalHoliday = memento.getInLegalHoliday();
		this.outLegalHoliday = memento.getOutLegalHoliday();
		this.startDay = memento.getStartDay();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WeekHolidaySettingSetMemento memento) {
		memento.setCID(this.CID);
		memento.setInLegalHoliday(this.inLegalHoliday);
		memento.setOutLegalHoliday(this.outLegalHoliday);
		memento.setStartDay(this.startDay);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CID == null) ? 0 : CID.hashCode());
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
		WeekHolidaySetting other = (WeekHolidaySetting) obj;
		if (CID == null) {
			if (other.CID != null)
				return false;
		} else if (!CID.equals(other.CID))
			return false;
		return true;
	}
}
