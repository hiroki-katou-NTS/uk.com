/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

import lombok.Data;

/**
 * The Class AvgEarnLevelMasterSetting.
 */
@Data
public class AvgEarnLevelMasterSetting {

	/** The code. */
	private Integer code;

	/** The health level. */
	private Integer healthLevel;

	/** The pension level. */
	private Integer pensionLevel;

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

	public AvgEarnLevelMasterSetting(Integer code, Integer healthLevel, Integer pensionLevel,
			Long avgEarn, Long salLimit) {
		super();
		this.code = code;
		this.healthLevel = healthLevel;
		this.pensionLevel = pensionLevel;
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
		this.code = memento.getCode();
		this.healthLevel = memento.getHealthLevel();
		this.pensionLevel = memento.getPensionLevel();
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
		memento.setCode(this.code);
		memento.setHealthLevel(this.healthLevel);
		memento.setPensionLevel(this.pensionLevel);
		memento.setAvgEarn(this.avgEarn);
		memento.setSalLimit(this.salLimit);
	}

}
