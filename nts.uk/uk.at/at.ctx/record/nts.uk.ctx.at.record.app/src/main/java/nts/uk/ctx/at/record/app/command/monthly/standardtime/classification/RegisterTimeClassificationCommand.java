/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;

import lombok.Getter;

/**
 * 選択した分類の目安時間設定を登録する
 */
@Getter
public class RegisterTimeClassificationCommand {

	// 会社ID 1
	private  String classificationCode;

	// 労働制 3
	private int laborSystemAtr;

	/** 1ヶ月 (Month) */

	//エラー時間
	private int errorTimeMonth1;

	//アラーム時間
	private int alarmTimeMonth1;

	// 上限時間
	private int upperLimitTimeMonth1;

	//エラー時間
	private int errorTimeMonth2;

	//アラーム時間
	private int alarmTimeMonth2;

	// 上限時間
	private int upperLimitTimeMonth2;

	/** 1年間 (Year) */

	//エラー時間
	private int errorTimeYear1;

	//アラーム時間
	private int alarmTimeYear1;

	// 上限時間
	private int upperLimitTimeYear1;

	//エラー時間
	private int errorTimeYear2;

	//アラーム時間
	private int alarmTimeYear2;

	// 上限時間
	private int upperLimitTimeYear2;

	/** 超過上限回数  */
	//超過上限回数
	private int overMaxTimes;

}
