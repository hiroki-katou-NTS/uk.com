/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.command.mastercopy;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.fasterxml.jackson.core.JsonParser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nts.arc.time.GeneralDateTime;

/**
 * The Class PerScheBatchCorrectProcessDto.
 */
// 個人スケジュールの一括修正処理
@Getter
@Setter
public class PerCopyDataCorrectProcessDto {
	
	/** The with error. */
	// エラー有無
	private WithError withError;
	
	/** The execution state. */
	// 実行状態
	private ExecutionState executionState;
	
	/** The start time. */
	// 開始時刻
	private GeneralDateTime startTime; 
	
	/** The end time. */
	// 終了時刻
	private GeneralDateTime endTime; 
	
	/** The errors. */
	// エラーリスト
	private List<ErrorContentDto> errors;
	
	/**
	 * Build error list content json
	 * @return error list content json
	 */
	private String buildJsonString() {
		JsonArrayBuilder arr = Json.createArrayBuilder();
		
		for (ErrorContentDto error : errors) {
			arr.add(error.buildJsonObject());
		}
		
		return arr.build().toString();
	}
	
	/**
	 * Create a json string from error content json array
	 * @return string of error content json array
	 */
	@Override
	public String toString() {
		return buildJsonString();
	}
}
