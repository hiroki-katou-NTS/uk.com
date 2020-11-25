package nts.uk.ctx.sys.portal.app.command.toppagealarm;

import lombok.Data;

@Data
public class ToppageAlarmDataUnreadCommand {

	private String companyId;
	
	private String sId;
	
	private Integer displayAtr;
	
	private Integer alarmClassification;
	
	private String identificationKey;
	
}
