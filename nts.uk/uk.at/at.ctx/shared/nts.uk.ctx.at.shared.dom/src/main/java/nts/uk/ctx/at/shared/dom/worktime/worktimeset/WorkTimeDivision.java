/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

//就業時間帯勤務区分
/**
 * The Class WorkTimeDivision.
 */
@Getter
@Builder
public class WorkTimeDivision extends WorkTimeDomainObject {

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
}
