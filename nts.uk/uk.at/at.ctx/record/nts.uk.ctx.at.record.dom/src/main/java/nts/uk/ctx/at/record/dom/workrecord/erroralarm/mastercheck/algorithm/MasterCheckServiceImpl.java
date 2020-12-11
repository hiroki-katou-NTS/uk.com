package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
@Stateless
public class MasterCheckServiceImpl implements MasterCheckService {

	@Inject
	private MasterCheckServiceResult checkService;
	@Override
	public void extractMasterCheck(String cid, List<String> lstSid, DatePeriod dPeriod, String errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType) {
		
		
		
		checkService.extractMasterCheckResult(cid, 
				lstSid, 
				dPeriod, errorMasterCheckId,
				getWplByListSidAndPeriod,
				lstStatusEmp,
				lstResultCondition,
				lstCheckType);
		
	}

}
