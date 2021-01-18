/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultRegisWorkSchedule {
	
	// 登録されたか
	private boolean isRegistered;
	// エラーがあるか
	private boolean hasError;
	// List<社員コード, 社員名, 年月日, エラー項目ID, エラーメッセージ>
	private List<ErrorInfomation> listErrorInfo;
}
