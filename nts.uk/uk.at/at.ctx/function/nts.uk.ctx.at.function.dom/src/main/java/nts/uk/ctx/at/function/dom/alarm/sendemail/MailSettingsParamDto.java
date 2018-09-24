package nts.uk.ctx.at.function.dom.alarm.sendemail;

import lombok.Data;

/**
 * 
 * @author thuongtv
 *
 */

@Data
public class MailSettingsParamDto {
	/** Subject email employee */
	private String subject;
	/** Content email employee */
	private String text;
	/** Subject email admin */
	private String subjectAdmin;
	/** Content email admin */
	private String textAdmin;
	
	public MailSettingsParamDto(String subject, String text, String subjectAdmin, String textAdmin) {
		super();
		this.subject = subject;
		this.text = text;
		this.subjectAdmin = subjectAdmin;
		this.textAdmin = textAdmin;
	}
}
