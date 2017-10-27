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
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class ErrorAlarmWorkRecordFinder {

	@Inject
	private ErrorAlarmWorkRecordRepository repository;

	public List<ErrorAlarmWorkRecordDto> getListErrorAlarmWorkRecord() {
		List<ErrorAlarmWorkRecord> lstDAta = repository.getListErrorAlarmWorkRecord(AppContexts.user().companyId());
		return lstDAta.stream().map(item -> ErrorAlarmWorkRecordDto.fromDomain(item)).collect(Collectors.toList());
	}

}
