/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.command.mastercopy;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ErrorContentDto.
 */
// エラーの内容
@Getter
@Setter
public class ErrorContentDto {

	/**  The constant MESSAGE. */
	private static final String MESSAGE = "message";
	
	/**  The constant CATEGORY_NAME. */
	private static final String CATEGORY_NAME = "categoryName";
	
	/**  The constant ORDER. */
	private static final String ORDER = "order";
	
	/**  The constant SYSTEM_TYPE. */
	private static final String SYSTEM_TYPE = "systemType";
	
	/** The message. */
	// メッセージ
	private String message;
	
	/** The category name. */
	// カテゴリ
	private String categoryName = "";
	
	/** The order. */
	// 並び順
	private Integer order;
	
	/** The system type. */
	// 区分
	private String systemType;
	
	/**
	 * Create a error content json object to transfer to client side.
	 * @return error content json object
	 */
	public JsonObject buildJsonObject() {
		JsonObjectBuilder tmp = Json.createObjectBuilder();
		tmp.add(MESSAGE, message);
		tmp.add(CATEGORY_NAME, categoryName);
		tmp.add(ORDER, order);
		tmp.add(SYSTEM_TYPE, systemType);		
		
		return tmp.build();
	}
}
