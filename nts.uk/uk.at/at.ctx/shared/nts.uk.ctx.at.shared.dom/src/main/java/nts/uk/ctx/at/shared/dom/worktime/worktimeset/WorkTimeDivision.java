/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.shr.com.primitive.Memo;

//就業時間帯勤務区分
/**
 * The Class WorkTimeDivision.
 */
@Getter
@NoArgsConstructor
public class WorkTimeDivision extends WorkTimeDomainObject implements Cloneable{

	/** The work time daily atr. */
	// 勤務形態区分
	private WorkTimeDailyAtr workTimeDailyAtr;

	/** The work time method set. */
	// 就業時間帯の設定方法
	private WorkTimeMethodSet workTimeMethodSet;
	
	
	/**
	 * フレックス勤務or流動勤務のどちらかに該当するか判定する
	 * @return　フレックスか流動である
	 */
	public boolean isfluidorFlex() {
		return workTimeDailyAtr.isFlex() || workTimeMethodSet.isFluidWork();
	}
	
	public boolean isFlow() {
		return workTimeDailyAtr.isRegular() && workTimeMethodSet.isFluidWork();
	}
	
	public boolean isFlex() {
		return workTimeDailyAtr.isFlex();
	}

	public boolean isFixed() {
		return workTimeMethodSet.isFixedWork();
	}
	
	/**
	 * 勤務形態を取得する
	 * @return 就業時間帯の勤務形態（固定勤務、フレックス勤務、流動勤務、時差勤務)
	 */
	public WorkTimeForm getWorkTimeForm() {
		if(this.isFlex())
			return WorkTimeForm.FLEX;
		
		switch(this.getWorkTimeMethodSet()) {
			case FIXED_WORK:		return WorkTimeForm.FIXED;
			case DIFFTIME_WORK:		return WorkTimeForm.TIMEDIFFERENCE;
			case FLOW_WORK:			return WorkTimeForm.FLOW;
			default:				throw new RuntimeException("Non-conformity No WorkTimeForm");
		}
	}
	
	/**
	 * Instantiates a new work time division.
	 *
	 * @param workTimeDailyAtr the work time daily atr
	 * @param workTimeMethodSet the work time method set
	 */
	public WorkTimeDivision(WorkTimeDailyAtr workTimeDailyAtr, WorkTimeMethodSet workTimeMethodSet) {
		super();
		this.workTimeDailyAtr = workTimeDailyAtr;
		this.workTimeMethodSet = workTimeMethodSet;
	}
	
	@Override
	public WorkTimeDivision clone() {
		WorkTimeDivision cloned = new WorkTimeDivision();
		try {
			cloned.workTimeDailyAtr = WorkTimeDailyAtr.valueOf(this.workTimeDailyAtr.value);
			cloned.workTimeMethodSet= WorkTimeMethodSet.valueOf(this.workTimeMethodSet.value);
		}	
		catch (Exception e){
			throw new RuntimeException("WorkTimeDivision clone error.");
		}
		return cloned;
	}
}
