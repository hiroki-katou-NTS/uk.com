/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ScheduleMethod.
 */
@Getter
// 予定作成方法
public class ScheduleMethod extends DomainObject {
	
	/** The basic create method. */
	// 基本作成方法
	private WorkScheduleBasicCreMethod basicCreateMethod;
	
	/** The work schedule bus cal. */
	// 営業日カレンダーによる勤務予定作成
	private Optional<WorkScheduleBusCal> workScheduleBusCal;
	
	/** The monthly pattern work schedule cre. */
	// 月間パターンによる勤務予定作成
	private Optional<MonthlyPatternWorkScheduleCre> monthlyPatternWorkScheduleCre;
	
	
	/**
	 * Instantiates a new schedule method.
	 *
	 * @param memento the memento
	 */
	public ScheduleMethod(ScheduleMethodGetMemento memento){
		this.basicCreateMethod = memento.getBasicCreateMethod();
		this.workScheduleBusCal = memento.getWorkScheduleBusCal();
		this.monthlyPatternWorkScheduleCre = memento.getMonthlyPatternWorkScheduleCre();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleMethodSetMemento memento) {
		memento.setBasicCreateMethod(this.basicCreateMethod);
		memento.setWorkScheduleBusCal(this.workScheduleBusCal);
		memento.setMonthlyPatternWorkScheduleCre(this.monthlyPatternWorkScheduleCre);
	}

	/**
	 * Instantiates a new schedule method.
	 *
	 * @param basicCreateMethod the basic create method
	 * @param workScheduleBusCal the work schedule bus cal
	 * @param monthlyPatternWorkScheduleCre the monthly pattern work schedule cre
	 */
	public ScheduleMethod(int basicCreateMethod, WorkScheduleBusCal workScheduleBusCal,
			MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre) {
		super();
		this.basicCreateMethod = EnumAdaptor.valueOf(basicCreateMethod, WorkScheduleBasicCreMethod.class);
		this.workScheduleBusCal = Optional.ofNullable(workScheduleBusCal);
		this.monthlyPatternWorkScheduleCre = Optional.ofNullable(monthlyPatternWorkScheduleCre);
	}
	
}
