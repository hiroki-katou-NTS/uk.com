/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.classification;


import lombok.Getter;

/**
 * 選択した分類の目安時間設定を削除する
 */
@Getter
public class DeleteTimeClassificationCommand {

	// 分類コード
	private  String classificationCode;

	// 労働制 3
	private int laborSystemAtr;

}
