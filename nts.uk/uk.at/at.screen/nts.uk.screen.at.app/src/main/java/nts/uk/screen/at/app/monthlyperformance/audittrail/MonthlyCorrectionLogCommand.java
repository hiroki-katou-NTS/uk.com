package nts.uk.screen.at.app.monthlyperformance.audittrail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyCorrectionLogCommand {
	
	private List<MonthlyRecordWorkDto> monthlyOld;
	
	private List<MonthlyRecordWorkDto> monthlyNew;
	
	private List<MonthlyModifyQuery> query;
	
	private GeneralDate endPeriod;
	
}
