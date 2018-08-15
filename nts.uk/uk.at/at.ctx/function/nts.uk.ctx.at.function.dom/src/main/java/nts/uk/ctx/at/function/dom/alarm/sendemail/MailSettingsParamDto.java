package nts.uk.ctx.at.function.dom.alarm.sendemail;

import lombok.Data;

/**
 * 
 * @author thuongtv
 *
 */

@Data
public class MailSettingsParamDto {
	private String subject;
	private String text;
	private String subjectAdmin;
	private String textAdmin;

}
