package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeOvertime{
	/**
	 * workTypeCode
	 */
	private String workTypeCode;
	/**
	 * workTypeName
	 */
	private String workTypeName;
}
