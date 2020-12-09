package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;

public interface MasterCheckService {
	public void extractMasterCheck(String cid, List<String> lstSid, DatePeriod dPeriod,	List<String> errorMasterCheckId
			,List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,List<StatusOfEmployeeAdapterAl> lstStatusEmp
			,List<ResultOfEachCondition> lstResultCondition,List<AlarmListCheckInfor> lstCheckType);
}
