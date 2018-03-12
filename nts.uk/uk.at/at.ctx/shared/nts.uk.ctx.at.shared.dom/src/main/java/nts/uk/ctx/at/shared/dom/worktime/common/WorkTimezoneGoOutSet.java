/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneGoOutSet.
 */
//就業時間帯の外出設定
@Getter
public class WorkTimezoneGoOutSet extends WorkTimeDomainObject {
	
	/** The total rounding set. */
	//合計丸め設定
	private TotalRoundingSet totalRoundingSet;
	
	/** The diff timezone setting. */
	//時間帯別設定
	private GoOutTimezoneRoundingSet diffTimezoneSetting;

	/**
	 * Instantiates a new work timezone go out set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneGoOutSet(WorkTimezoneGoOutSetGetMemento memento) {
		this.totalRoundingSet = memento.getTotalRoundingSet();
		this.diffTimezoneSetting = memento.getDiffTimezoneSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneGoOutSetSetMemento memento) {
		memento.setTotalRoundingSet(this.totalRoundingSet);
		memento.setDiffTimezoneSetting(this.diffTimezoneSetting);
	}
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param oldDomain the old domain
	 */
	public void restoreData(ScreenMode screenMode, WorkTimezoneGoOutSet oldDomain) {
		// Simple mode
		if (screenMode == ScreenMode.SIMPLE) {
			this.totalRoundingSet.restoreData(oldDomain.getTotalRoundingSet());			
		} 
		
		// Go deeper
		this.diffTimezoneSetting.restoreData(screenMode, oldDomain.getDiffTimezoneSetting());
	}
	
	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		// Simple mode
		if (screenMode == ScreenMode.SIMPLE) {
			this.totalRoundingSet.restoreDefaultData();			
		} 
		
		// Go deeper
		this.diffTimezoneSetting.restoreDefaultData(screenMode);
	}
}
