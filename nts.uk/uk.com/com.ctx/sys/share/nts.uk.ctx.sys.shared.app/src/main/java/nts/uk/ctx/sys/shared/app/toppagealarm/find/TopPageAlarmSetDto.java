package nts.uk.ctx.sys.shared.app.toppagealarm.find;

import lombok.Value;
/**
 * 
 * @author yennth
 *
 */
@Value
public class TopPageAlarmSetDto {
	private String companyId;
	private int alarmCategory;
	private int useAtr;
}
