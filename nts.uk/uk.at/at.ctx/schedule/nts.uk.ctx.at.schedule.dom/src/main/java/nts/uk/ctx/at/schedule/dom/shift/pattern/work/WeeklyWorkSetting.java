/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WeeklyWorkDay.
 */
// 週間勤務設定
@Getter
public class WeeklyWorkSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The day of week. */
	//曜日
	private DayOfWeek dayOfWeek;
	
	/** The workday division. */
	// 稼働日区分
	private WorkdayDivision workdayDivision;
	
	
	/**
	 * Instantiates a new weekly work setting.
	 *
	 * @param memento the memento
	 */
	public WeeklyWorkSetting(WeeklyWorkSettingGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.dayOfWeek = memento.getDayOfWeek();
		this.workdayDivision = memento.getWorkdayDivision();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WeeklyWorkSettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setDayOfWeek(this.dayOfWeek);
		memento.setWorkdayDivision(this.workdayDivision);
	}

}
