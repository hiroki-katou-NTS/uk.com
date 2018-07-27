package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class EmployeeErrorPubExport {
	
	/** 処理年月日: 年月日*/
	private GeneralDate date;
	
	private Boolean hasError;

}
