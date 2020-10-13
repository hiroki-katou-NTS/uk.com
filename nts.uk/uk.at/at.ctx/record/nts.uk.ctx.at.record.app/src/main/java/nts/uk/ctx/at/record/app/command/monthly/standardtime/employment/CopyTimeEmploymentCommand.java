/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.employment;


import lombok.Getter;

/**
 * 選択した雇用の目安時間設定を別の雇用へ複写する
 */
@Getter
public class CopyTimeEmploymentCommand {

	// 雇用コード
	private String employmentCD;

	// 雇用コード
	private String employmentCDCoppy;

	// 労働制 3
	private Integer laborSystemAtr;

}
