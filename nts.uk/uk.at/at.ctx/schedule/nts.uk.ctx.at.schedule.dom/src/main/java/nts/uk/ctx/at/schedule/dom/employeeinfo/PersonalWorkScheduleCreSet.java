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

}
