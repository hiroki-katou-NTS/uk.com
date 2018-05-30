package nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;

//エラー／アラームのチェック条件をすべて取得する
@Stateless
public class ConditionAlarmError {
	
	@Inject 
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
//	@Inject
//	private ErrorAlarmConditionRepository errorAlarmConditionRepository;
	
	public List<ErrorAlarmWorkRecord> getErAlConditons(String companyId){
		List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository.getAllErAlCompany(companyId);
//		List<String> listEralCheckId = errorAlarmWorkRecords.stream().map(x -> x.getErrorAlarmCheckID())
//				.filter(y -> y != null).collect(Collectors.toList());
		return errorAlarmWorkRecords;
	}

	public List<Map<String, Object>> getErAlConditonByComID(String companyId){
		return errorAlarmWorkRecordRepository.getErAlByComID(companyId);
	}
}
