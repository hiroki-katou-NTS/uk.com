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
	
	private String siftCD;

	private String workTypeCode;
	
	private String inputDate;
	
	private String employeeID;
}
