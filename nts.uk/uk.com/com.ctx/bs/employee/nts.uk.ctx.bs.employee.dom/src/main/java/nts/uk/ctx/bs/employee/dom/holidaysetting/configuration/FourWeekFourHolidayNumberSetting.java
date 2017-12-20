/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
// 4週4休日数設定
public class FourWeekFourHolidayNumberSetting extends AggregateRoot{
	
	/** The is one week holiday. */
	// 1週の休日チェックをする
	private boolean isOneWeekHoliday;
	
	/** The one week. */
	// 1週間
	private OneWeekPublicHoliday oneWeek;
	
	/** The is four week holiday. */
	// 4週の休日チェックをする
	private boolean isFourWeekHoliday;
	
	/** The four week. */
	// 4週間
	private FourWeekPublicHoliday fourWeek;
	
	/** The cid. */
	// 会社ID
	private String CID; 
	
	public FourWeekFourHolidayNumberSetting(FourWeekFourHolidayNumberSettingGetMemento memento) {
		this.isOneWeekHoliday = memento.getIsOneWeekHoliday();
		this.oneWeek = memento.getOneWeek();
		this.isFourWeekHoliday = memento.getIsFourWeekHoliday();
		this.fourWeek = memento.getFourWeek();
		this.CID = memento.getCID();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FourWeekFourHolidayNumberSettingSetMemento memento) {
		memento.setIsOneWeekHoliday(this.isOneWeekHoliday);
		memento.setOneWeek(this.oneWeek);
		memento.setIsFourWeekHoliday(this.isFourWeekHoliday);
		memento.setFourWeek(this.fourWeek);
		memento.setCID(this.CID);
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
		FourWeekFourHolidayNumberSetting other = (FourWeekFourHolidayNumberSetting) obj;
		if (CID == null) {
			if (other.CID != null)
				return false;
		} else if (!CID.equals(other.CID))
			return false;
		return true;
	}
}
