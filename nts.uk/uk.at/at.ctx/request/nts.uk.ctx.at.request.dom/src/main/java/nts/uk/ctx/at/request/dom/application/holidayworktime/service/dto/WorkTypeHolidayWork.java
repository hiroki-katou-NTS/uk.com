package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class WorkTypeHolidayWork {

	/**
	 * workTypeCodes
	 */
	private List<String> workTypeCodes;
	/**
	 * workTypeCode
	 */
	private String workTypeCode;
	/**
	 * workTypeName
	 */
	private String workTypeName;
}
