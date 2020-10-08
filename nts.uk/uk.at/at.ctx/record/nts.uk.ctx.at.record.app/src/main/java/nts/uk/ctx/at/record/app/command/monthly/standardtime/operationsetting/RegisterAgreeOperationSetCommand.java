/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting;


import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 運用設定を更新登録する
 */
@Getter
public class RegisterAgreeOperationSetCommand {

	// ３６協定起算月
	private int startingMonth;

	// 締め日
	private int closureDay;

	// 締め日
	private Boolean lastDayOfMonth;

	//特別条項申請を使用する
	private Boolean specialConditionApplicationUse;

	// 年間の特別条項申請を使用する
	private Boolean yearSpecicalConditionApplicationUse;

}
