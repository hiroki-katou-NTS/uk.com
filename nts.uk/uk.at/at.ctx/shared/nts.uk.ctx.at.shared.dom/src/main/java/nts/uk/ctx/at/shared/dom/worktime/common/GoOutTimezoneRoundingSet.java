/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class GoOutTimezoneRoundingSet.
 */
//時間帯別外出丸め設定
@Getter
public class GoOutTimezoneRoundingSet extends DomainObject {
	
	/** The pub hol work timezone. */
	//休日出勤時間帯
	private GoOutTypeRoundingSet pubHolWorkTimezone;
	
	/** The work timezone. */
	//就業時間帯
	private GoOutTypeRoundingSet workTimezone;
	
	/** The ottimezone. */
	//残業時間帯
	private GoOutTypeRoundingSet ottimezone;
	
	/**
	 * Instantiates a new go out timezone rounding set.
	 *
	 * @param memento the memento
	 */
	public GoOutTimezoneRoundingSet(GoOutTimezoneRoundingSetGetMemento memento) {
		this.pubHolWorkTimezone = memento.getPubHolWorkTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.ottimezone = memento.getOttimezone();
	}
	
	/**
	 * Save to mememto.
	 *
	 * @param memento the memento
	 */
	public void saveToMememto(GoOutTimezoneRoundingSetSetMemento memento){
		memento.setPubHolWorkTimezone(this.pubHolWorkTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setOttimezone(this.ottimezone);
	}
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param oldDomain the old domain
	 */
	public void restoreData(ScreenMode screenMode, GoOutTimezoneRoundingSet oldDomain) {
		this.pubHolWorkTimezone.restoreData(screenMode, oldDomain.getPubHolWorkTimezone());
		this.workTimezone.restoreData(screenMode, oldDomain.getWorkTimezone());
		this.ottimezone.restoreData(screenMode, oldDomain.getOttimezone());		
	}
	
	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		this.pubHolWorkTimezone.restoreDefaultData(screenMode);
		this.workTimezone.restoreDefaultData(screenMode);
		this.ottimezone.restoreDefaultData(screenMode);		
	}
}
