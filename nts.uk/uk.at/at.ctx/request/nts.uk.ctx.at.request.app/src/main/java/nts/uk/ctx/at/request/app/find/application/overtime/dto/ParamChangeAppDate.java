package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Value
public class ParamChangeAppDate {
	/**
	 * appDate
	 */
	private String appDate;
	/**
	 * prePostAtr
	 */
	private int prePostAtr;
	/**
	 * siftCD
	 */
	private String siftCD;
	
	/**
	 * overtimeHours
	 */
	private List<CaculationTime> overtimeHours;

}
