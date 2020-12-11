package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.MasterCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.AlarmListPersonExtractServicePub;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
@Stateless
public class AlarmListPersonExtractServicePubImpl implements AlarmListPersonExtractServicePub{
	@Inject
	private MasterCheckService masterCheck;
	@Override
	public void extractMasterCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod,
			String errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, 
			List<AlarmListCheckInfor> lstCheckType) {
		masterCheck.extractMasterCheck(cid, lstSid, dPeriod, 
				errorMasterCheckId, getWplByListSidAndPeriod, lstStatusEmp, lstResultCondition, lstCheckType);
		
	}

}
