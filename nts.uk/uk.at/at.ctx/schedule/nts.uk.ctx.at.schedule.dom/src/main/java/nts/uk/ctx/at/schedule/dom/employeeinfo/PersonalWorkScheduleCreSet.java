/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PersonalWorkScheduleCreSet.
 */
// 個人勤務予定作成設定
@Getter
public class PersonalWorkScheduleCreSet extends AggregateRoot{
	
	/** The basic create method. */
	// 基本作成方法
	private WorkScheduleBasicCreMethod basicCreateMethod;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The monthly pattern work schedule cre. */
	// 月間パターンによる勤務予定作成
	private MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre;
	
	
	/** The work schedule bus cal. */
	// 営業日カレンダーによる勤務予定作成
	private WorkScheduleBusCal workScheduleBusCal;


	/**
	 * Instantiates a new personal work schedule cre set.
	 *
	 * @param memento the memento
	 */
	public PersonalWorkScheduleCreSet(PersonalWorkScheduleCreSetGetMemento memento) {
		this.employeeId = memento.getEmployeeId();
		this.basicCreateMethod = memento.getBasicCreateMethod();
		this.monthlyPatternWorkScheduleCre = memento.getMonthlyPatternWorkScheduleCre();
		this.workScheduleBusCal = memento.getWorkScheduleBusCal();
	}

	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PersonalWorkScheduleCreSetSetMemento memento){
		memento.setEmployeeId(this.employeeId);
		memento.setBasicCreateMethod(this.basicCreateMethod);
		memento.setMonthlyPatternWorkScheduleCre(this.monthlyPatternWorkScheduleCre);
		memento.setWorkScheduleBusCal(this.workScheduleBusCal);
	}
}
