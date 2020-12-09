package nts.uk.ctx.at.function.ac.alarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.alarm.AlarmListPersonServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.AlarmListPersonExtractServicePub;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
@Stateless
public class AlarmListPersonServiceAdapterImpl implements AlarmListPersonServiceAdapter{
	@Inject
	private AlarmListPersonExtractServicePub extractService;

	@Override
	public void extractMasterCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod,
			List<String> errorMasterCheckId, List<WorkPlaceHistImport> lstWplHist,
			List<StatusOfEmployeeAdapter> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckInfor) {
		List<WorkPlaceHistImportAl> lstWkpIdAndPeriod = lstWplHist.stream().map(x -> 
					new WorkPlaceHistImportAl(x.getEmployeeId(), 
							x.getLstWkpIdAndPeriod().stream()
							.map(a -> new WorkPlaceIdAndPeriodImportAl(a.getDatePeriod(), a.getWorkplaceId())).collect(Collectors.toList()))
				).collect(Collectors.toList());
		List<StatusOfEmployeeAdapterAl> lstStaEmp = lstStatusEmp.stream()
				.map(x -> new StatusOfEmployeeAdapterAl(x.getEmployeeId(), x.getListPeriod())).collect(Collectors.toList());
		extractService.extractMasterCheckResult(cid, 
				lstSid, 
				dPeriod,
				errorMasterCheckId,
				lstWkpIdAndPeriod,
				lstStaEmp,
				lstResultCondition,
				lstCheckInfor);
		
	}

}
