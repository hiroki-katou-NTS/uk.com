/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;

import lombok.Getter;

/**
 * 選択した職場の目安時間設定を登録する
 */
@Getter
public class RegisterTimeWorkPlaceCommand {

	// 会社ID 1
	private  String workplaceId;

	// 労働制 3
	private Integer laborSystemAtr;

	/** 1ヶ月 (Month) */

	//エラー時間
	private Integer errorOneMonth;

	//アラーム時間
	private Integer alarmOneMonth;

	// 上限時間
	private Integer limitOneMonth;

	//エラー時間
	private Integer errorTwoMonths;

	//アラーム時間
	private Integer alarmTwoMonths;

	// 上限時間
	private Integer limitTwoMonths;

	/** 1年間 (Year) */

	//エラー時間
	private Integer errorOneYear;

	//アラーム時間
	private Integer alarmOneYear;

	// 上限時間
	private Integer limitOneYear;

	//エラー時間
	private Integer errorTwoYear;

	//アラーム時間
	private Integer alarmTwoYear;

	// 上限時間
	private Integer limitTwoYear;

	/** 超過上限回数  */
	//超過上限回数
	private Integer overMaxTimes;

}
