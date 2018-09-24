/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new data infor return.
 * @author HoangDD
 */
@Data
@NoArgsConstructor
public class DataInforReturnDto {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private int id;

	/**
	 * Instantiates a new data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 * @param id the id
	 */
	public DataInforReturnDto(String code, String name, int id) {
		super();
		this.code = code;
		this.name = name;
		this.id = id;
	}
	
	
}
