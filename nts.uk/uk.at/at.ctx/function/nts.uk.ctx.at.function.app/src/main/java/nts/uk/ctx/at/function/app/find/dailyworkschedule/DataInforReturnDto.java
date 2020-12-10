/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LienPTK
 */
@Data
@NoArgsConstructor
public class DataInforReturnDto {
	
	/** コード */
	private String code;
	
	/** 名称 */
	private String name;

	private int id;

	/**
	 * Instantiates a new data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 * @param layoutId the layout id
	 */
	public DataInforReturnDto(String code, String name, int id) {
		super();
		this.code = code;
		this.name = name;
		this.id = id;
	}

}
