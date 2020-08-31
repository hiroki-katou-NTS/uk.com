/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出力項目設定
 * @author LienPTK
 */
@Data
@NoArgsConstructor
public class DataInforReturnDto {
	
	/** コード */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 出力レイアウトID */
	private String layoutId;

	/**
	 * Instantiates a new data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 * @param layoutId the layout id
	 */
	public DataInforReturnDto(String code, String name, String layoutId) {
		super();
		this.code = code;
		this.name = name;
		this.layoutId = layoutId;
	}

}
