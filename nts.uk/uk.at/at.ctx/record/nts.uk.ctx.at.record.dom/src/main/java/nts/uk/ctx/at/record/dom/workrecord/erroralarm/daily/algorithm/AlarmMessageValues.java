package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmMessageValues {
	private String attendentName;
	private String workTypeName;
	private String wtName;
	private String alarmTarget;
}
