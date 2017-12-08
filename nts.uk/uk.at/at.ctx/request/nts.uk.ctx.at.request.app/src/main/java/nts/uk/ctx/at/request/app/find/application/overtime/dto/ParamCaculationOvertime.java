package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Value
public class ParamCaculationOvertime {
	/**
	 * OvertimeInputDtos
	 */
	private List<CaculationTime> overtimeHours;
	
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
	

}
