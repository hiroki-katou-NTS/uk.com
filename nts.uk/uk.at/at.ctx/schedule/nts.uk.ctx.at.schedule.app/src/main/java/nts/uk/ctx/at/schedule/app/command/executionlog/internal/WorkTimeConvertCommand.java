/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class WorkTimeConvertCommand.
 */
@Getter
@Setter
@NoArgsConstructor
// 「入力パラメータ」 社員ID; 年月日 ; 就業時間帯の参照先 ; 勤務種類コード ; 就業時間帯コード
public class WorkTimeConvertCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The base getter. */
	// 実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	// 就業時間帯の参照先
	private int referenceWorkingHours;
	
	/** The work type code. */
	// 勤務種類コード
	private String workTypeCode;
	
	/** The working code. */
	// 就業時間帯コード
	private String workingCode;

	public WorkTimeConvertCommand(String employeeId, ScheduleErrorLogGeterCommand baseGetter, int referenceWorkingHours,
			String workTypeCode, String workingCode) {
		super();
		this.employeeId = employeeId;
		this.baseGetter = baseGetter;
		this.referenceWorkingHours = referenceWorkingHours;
		this.workTypeCode = workTypeCode;
		this.workingCode = workingCode;
	}
	
	
}
