package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;

public interface MasterCheckService {
	/**
	 * マスタチェックの固有抽出項目のアラーム値を作成する
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param lstMasterCheckItem
	 * @param lstMasterCheck
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	public void extractMasterCheck(String cid, List<String> lstSid, DatePeriod dPeriod,	String errorMasterCheckId
			,List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,List<StatusOfEmployeeAdapterAl> lstStatusEmp
			,List<ResultOfEachCondition> lstResultCondition,List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
}
