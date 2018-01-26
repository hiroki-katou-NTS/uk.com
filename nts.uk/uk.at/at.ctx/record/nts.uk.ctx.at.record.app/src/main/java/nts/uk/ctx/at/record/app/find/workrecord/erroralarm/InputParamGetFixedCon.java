package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputParamGetFixedCon {
	private String errorAlarmCode;
	
	private int fixConWorkRecordNo;
	
}
