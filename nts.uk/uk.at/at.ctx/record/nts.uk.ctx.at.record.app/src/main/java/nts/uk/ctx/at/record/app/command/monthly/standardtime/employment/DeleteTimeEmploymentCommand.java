/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;


import lombok.Getter;

/**
 * 選択した雇用の目安時間設定を削除する
 */
@Getter
public class DeleteTimeEmploymentCommand {

	// 雇用コード 2
	private String employmentCD;

	// 労働制 3
	private Integer laborSystemAtr;

}
