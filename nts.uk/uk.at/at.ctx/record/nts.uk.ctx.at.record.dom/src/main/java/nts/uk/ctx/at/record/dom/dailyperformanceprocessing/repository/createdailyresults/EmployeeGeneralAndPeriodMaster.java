package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;

@AllArgsConstructor
@Data
public class EmployeeGeneralAndPeriodMaster {
	
	EmployeeGeneralInfoImport employeeGeneralInfoImport;
	
	PeriodInMasterList periodInMasterList ;
}
