package nts.uk.ctx.at.function.dom.alarm.sendemail;
/*
 * author : thuongtv
 */
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SendEmailParamDto {
	/** List employee ID */
	private List<String> employeeTagetIds;
	/** List manager ID */
	private List<String> managerTagetIds;
	/** List alarm to send */
	private List<ValueExtractAlarmDto> valueExtractAlarmDtos;
	/** Information subject content email */
	private MailSettingsParamDto mailSettingsParamDto;
	/** functionID */
	private Integer functionID;
}
