/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

import lombok.Data;

/**
 * The Class AvgEarnLevelMasterSetting.
 */
@Data
public class AvgEarnLevelMasterSetting {

	/** The grade. */
	private Integer grade;

	/** The avg earn. */
	private Long avgEarn;

	/** The sal limit. */
	private Long salLimit;

	/**
	 * Instantiates a new avg earn level master setting.
	 */
	public AvgEarnLevelMasterSetting() {
		super();
	}

	/**
	 * Instantiates a new avg earn level master setting.
	 *
	 * @param grade the grade
	 * @param avgEarn the avg earn
	 * @param salLimit the sal limit
	 */
	public AvgEarnLevelMasterSetting(Integer grade, Long avgEarn, Long salLimit) {
		super();
		this.grade = grade;
		this.avgEarn = avgEarn;
		this.salLimit = salLimit;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public AvgEarnLevelMasterSetting(AvgEarnLevelMasterSettingGetMemento memento) {
		this.grade = memento.getGrade();
		this.avgEarn = memento.getAvgEarn();
		this.salLimit = memento.getSalLimit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AvgEarnLevelMasterSettingSetMemento memento) {
		memento.setGrade(this.grade);
		memento.setAvgEarn(this.avgEarn);
		memento.setSalLimit(this.salLimit);
	}

}
