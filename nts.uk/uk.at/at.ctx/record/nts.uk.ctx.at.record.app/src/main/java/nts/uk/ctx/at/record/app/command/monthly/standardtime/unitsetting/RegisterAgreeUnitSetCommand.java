/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting;


import lombok.Getter;

/**
 * 利用単位設定を更新登録する
 */
@Getter
public class RegisterAgreeUnitSetCommand {

	// 分類使用区分
	private int classificationUseAtr;

	// 雇用使用区分
	private int employmentUseAtr;

	// 職場使用区分
	private int workPlaceUseAtr;

}
