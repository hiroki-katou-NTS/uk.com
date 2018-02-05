package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class WorkTimeHolidayWork {
	/**
	 * workTimeCodes
	 */
	private List<String> workTimeCodes;
	/**
	 * WorkTimeCode
	 */
	private String WorkTimeCode;
	/**
	 * workTimeName
	 */
	private String workTimeName;

}
