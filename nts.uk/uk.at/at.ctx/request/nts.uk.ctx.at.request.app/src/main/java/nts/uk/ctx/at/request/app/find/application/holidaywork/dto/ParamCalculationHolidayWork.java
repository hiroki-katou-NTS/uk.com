package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
@Data
public class ParamCalculationHolidayWork {
	/**
	 * OvertimeInputDtos
	 */
	private List<CaculationTime> breakTimes;
	
	/**
	 * bonusTimes
	 */
	private List<CaculationTime> bonusTimes;
	/**
	 * prePostAtr
	 */
	private int prePostAtr;
	/**
	 * appDate
	 */
	private String appDate;
	
	/**
	 * siftCD
	 */
	private String siftCD;

	/**
	 * workTypeCode
	 */
	private String workTypeCode;
	
	/**
	 * inputDate
	 */
	private String inputDate;
	
	/**
	 * employeeID
	 */
	private String employeeID;
	/**
	 * startTime
	 */
	private Integer startTime;
	/**
	 * endTime
	 */
	private Integer endTime;
	/**
	 * startTimeRest
	 */
	private List<Integer> startTimeRests;
	/**
	 * endTimeRest
	 */
	private List<Integer> endTimeRests;
}
