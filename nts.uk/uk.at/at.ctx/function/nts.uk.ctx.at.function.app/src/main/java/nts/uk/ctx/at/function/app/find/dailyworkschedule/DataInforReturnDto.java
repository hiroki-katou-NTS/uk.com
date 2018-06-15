/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;

/**
 * Instantiates a new data infor return.
 * @author HoangDD
 */
@Data
public class DataInforReturnDto {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 */
	public DataInforReturnDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	
}
