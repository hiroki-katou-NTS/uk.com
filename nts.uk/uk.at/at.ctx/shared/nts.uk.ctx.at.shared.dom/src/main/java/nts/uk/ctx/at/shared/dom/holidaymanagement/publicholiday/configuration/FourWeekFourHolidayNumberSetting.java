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
}
