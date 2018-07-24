package nts.uk.ctx.at.function.dom.adapter.alarm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author thuongtv
 */
@Data
@AllArgsConstructor
public class MailDestinationAlarmImport {
	private String employeeID;

	private List<OutGoingMailAlarm> outGoingMails;
}
