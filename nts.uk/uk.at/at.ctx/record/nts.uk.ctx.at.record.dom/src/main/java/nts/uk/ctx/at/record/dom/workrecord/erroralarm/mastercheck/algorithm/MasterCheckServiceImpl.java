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
	private MasterCheckFixedExtractConditionRepository masterCheckRepos;
	@Inject
	private MasterCheckFixedExtractItemRepository masterCheckItemRepos;
	@Inject
	private MasterCheckServiceResult checkService;
	@Override
	public void extractMasterCheck(String cid, List<String> lstSid, DatePeriod dPeriod, List<String> errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType) {
		//ドメインモデル「マスタチェックの固定抽出条件」を取得
		List<MasterCheckFixedExtractCondition> lstMasterCheck = masterCheckRepos.findAll(errorMasterCheckId, true);
		List<MasterCheckFixedCheckItem> lstItemNo = lstMasterCheck.stream().map(x -> x.getNo()).collect(Collectors.toList());
		//ドメインモデル「マスタチェックの固有抽出項目」を取得する
		List<Integer> lstNo = lstItemNo.stream().map(x -> x.value)
				.collect(Collectors.toList());
		List<MasterCheckFixedExtractItem> lstMasterCheckItem = masterCheckItemRepos.getFixedMasterCheckByNo(lstNo);
		checkService.prepareDateBeforeCheck(cid, lstSid, lstItemNo, dPeriod);
		checkService.extractMasterCheckResult(cid, 
				lstSid, 
				dPeriod,
				lstMasterCheckItem,
				lstMasterCheck,
				getWplByListSidAndPeriod,
				lstStatusEmp,
				lstResultCondition,
				lstCheckType);
		
	}

}
