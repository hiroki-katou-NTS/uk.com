package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.Value;

@Value
public class ParamCaculationOvertime {
	/**
	 * OvertimeInputDtos
	 */
	private List<OvertimeInputDto> OvertimeInputDtos;
	/**
	 * prePostAtr
	 */
	private int prePostAtr;
	/**
	 * appDate
	 */
	private String appDate;
	

}
