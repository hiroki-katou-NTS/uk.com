/**
 * 4:55:11 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class ErrorAlarmWorkRecordFinder {

	@Inject
	private ErrorAlarmWorkRecordRepository repository;

	/**
	 * 
	 * @param type = 0 => user setting, type = 1 => system setting
	 * @return
	 */
	public List<ErrorAlarmWorkRecordDto> getListErrorAlarmWorkRecord(int type) {
		List<ErrorAlarmWorkRecord> lstErrorAlarm = repository
				.getListErrorAlarmWorkRecord(AppContexts.user().companyId(), type);
		if (type == 1) {
			lstErrorAlarm = lstErrorAlarm.stream().filter(eral -> eral.getCode().v().startsWith("S")).collect(Collectors.toList());
		}
		List<ErrorAlarmWorkRecordDto> lstDto = lstErrorAlarm.stream()
				.map(eral -> ErrorAlarmWorkRecordDto.fromDomain(eral, eral.getErrorAlarmCondition()))
				.collect(Collectors.toList());
		return lstDto;
	}

	public List<ErrorAlarmWorkRecordDto> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmWorkRecord> lstErrorAlarm = repository.findByListErrorAlamCheckId(listEralCheckId);
		List<ErrorAlarmCondition> lstCondition = repository.findConditionByListErrorAlamCheckId(listEralCheckId);
		List<ErrorAlarmWorkRecordDto> lstDto = lstErrorAlarm.stream().map(eral -> {
			for (ErrorAlarmCondition errorAlarmCondition : lstCondition) {
				if(errorAlarmCondition.getErrorAlarmCheckID().equals(eral.getErrorAlarmCheckID())){
					return ErrorAlarmWorkRecordDto.fromDomain(eral, errorAlarmCondition);
				}
			}
			return null;
		}).collect(Collectors.toList());
		return lstDto;
	}
}
