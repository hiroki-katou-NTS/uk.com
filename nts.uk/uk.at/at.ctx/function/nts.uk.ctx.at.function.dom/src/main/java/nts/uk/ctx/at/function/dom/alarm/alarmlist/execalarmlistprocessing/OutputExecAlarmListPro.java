package nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputExecAlarmListPro {

	private boolean checkExecAlarmListPro;
	
	private String errorMessage;

	public OutputExecAlarmListPro(boolean checkExecAlarmListPro, String errorMessage) {
		super();
		this.checkExecAlarmListPro = checkExecAlarmListPro;
		this.errorMessage = errorMessage;
	}
	
}
