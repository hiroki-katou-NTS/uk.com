package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
public class IdentityVerifiForDay {
	// 社員ID
	private String employeeID;
	
	//　年月日
	private GeneralDate processingYmd;
	
	// 本人状況
	private String personSituation;
}
