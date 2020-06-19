package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkInfoOfDailyPerformance;


//勤務情報を変更する
public interface ReflectWorkInformationDomainService {
	//public boolean changeWorkInformation(String employeeId, GeneralDate date);
	public boolean changeWorkInformation(WorkInfoOfDailyPerformance workInfo,String companyId);
	
}
