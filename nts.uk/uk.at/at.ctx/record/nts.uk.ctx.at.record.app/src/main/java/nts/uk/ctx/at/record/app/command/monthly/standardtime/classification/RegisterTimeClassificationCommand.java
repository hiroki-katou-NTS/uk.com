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
	private Integer laborSystemAtr;

	/** 1ヶ月 (Month) */

	//エラー時間
	private Integer errorTimeMonth1;

	//アラーム時間
	private Integer alarmTimeMonth1;

	// 上限時間
	private Integer upperLimitTimeMonth1;

	//エラー時間
	private Integer errorTimeMonth2;

	//アラーム時間
	private Integer alarmTimeMonth2;

	// 上限時間
	private Integer upperLimitTimeMonth2;

	/** 1年間 (Year) */

	//エラー時間
	private Integer errorTimeYear1;

	//アラーム時間
	private Integer alarmTimeYear1;

	// 上限時間
	private Integer upperLimitTimeYear1;

	//エラー時間
	private Integer errorTimeYear2;

	//アラーム時間
	private Integer alarmTimeYear2;

	// 上限時間
	private Integer upperLimitTimeYear2;

	/** 超過上限回数  */
	//超過上限回数
	private Integer overMaxTimes;

}
