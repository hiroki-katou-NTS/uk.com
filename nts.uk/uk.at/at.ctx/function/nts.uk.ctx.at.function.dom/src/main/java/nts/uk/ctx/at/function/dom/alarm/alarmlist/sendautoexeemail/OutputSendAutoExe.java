package nts.uk.ctx.at.function.dom.alarm.alarmlist.sendautoexeemail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutputSendAutoExe {
	private ExtractionState extractionState;
	
	private String errorMessage;
}
