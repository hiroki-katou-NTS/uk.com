package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;


//勤務情報を変更する
public interface ReflectWorkInformationDomainService {
	//public boolean changeWorkInformation(String employeeId, GeneralDate date);
	public boolean changeWorkInformation(IntegrationOfDaily integrationOfDaily,String companyId,String employeeId,GeneralDate ymd);
	
}
