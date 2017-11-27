/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PerScheBatchCorrectProcessDto.
 */
// 個人スケジュールの一括修正処理
@Getter
@Setter
public class PerScheBatchCorrectProcessDto {
	
	/** The with error. */
	// エラー有無
	private WithError withError;
	
	/** The execution state. */
	// 実行状態
	private ExecutionState executionState;
	
	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime; 
	
	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime; 
	
	/** The errors. */
	// エラーリスト
	private Optional<ErrorContentDto> errors;

}
