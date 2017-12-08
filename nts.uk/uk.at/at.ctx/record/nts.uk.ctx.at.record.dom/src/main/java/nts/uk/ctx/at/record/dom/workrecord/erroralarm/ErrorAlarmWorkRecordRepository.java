/**
 * 10:39:05 AM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

/**
 * @author hungnm
 *
 */
public interface ErrorAlarmWorkRecordRepository {

	// get error by error code
	ErrorAlarmWorkRecord findByCode(String code);

	// get all errors and alarms's settings of login company
	List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId);

	// update an error/alarm setting
	void addErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain);

	// update an error/alarm setting
	void updateErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain);

	// remove an error/alarm setting
	void removeErrorAlarmWorkRecord(String code);

}
