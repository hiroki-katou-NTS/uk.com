/**
 * 4:55:11 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
		return repository.getListErrorAlarmWorkRecord(AppContexts.user().companyId()).stream()
				.map(domain -> ErrorAlarmWorkRecordDto.fromDomain(domain)).collect(Collectors.toList());
	}

	public ErrorAlarmWorkRecordDto findByCode(String code) {
		return ErrorAlarmWorkRecordDto.fromDomain(repository.findByCode(code).get());
	}

	public List<ErrorAlarmWorkRecordDto> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		return repository.findByListErrorAlamCheckId(listEralCheckId).stream()
				.map(domain -> ErrorAlarmWorkRecordDto.fromDomain(domain)).collect(Collectors.toList());
	}
}
