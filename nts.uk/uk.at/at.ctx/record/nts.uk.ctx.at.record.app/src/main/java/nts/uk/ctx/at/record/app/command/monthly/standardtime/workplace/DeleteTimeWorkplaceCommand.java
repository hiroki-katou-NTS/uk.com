/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace;


import lombok.Getter;
import lombok.Setter;

/**
 * 選択した雇用の目安時間設定を削除する
 */
@Getter
public class DeleteTimeWorkplaceCommand {

	// 会社ID 1
	private  String workplaceId;

	// 労働制 3
	private Integer laborSystemAtr;

}
