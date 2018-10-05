/**
 * 10:39:05 AM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;

/**
 * @author hungnm
 *
 */
public interface ErrorAlarmWorkRecordRepository {

	// get error by error code
	Optional<ErrorAlarmWorkRecord> findByCode(String code);

	// get error by eralCheckId
	Optional<ErrorAlarmWorkRecord> findByErrorAlamCheckId(String eralCheckId);

	// get error by list eralCheckId
	List<ErrorAlarmWorkRecord> findByListErrorAlamCheckId(List<String> listEralCheckId);

	// get condition by eralCheckId
	Optional<ErrorAlarmCondition> findConditionByErrorAlamCheckId(String eralCheckId);

	// get condition by list eralCheckId
	List<ErrorAlarmCondition> findConditionByListErrorAlamCheckId(List<String> listEralCheckId);

	// get all errors and alarms's settings of login company
	List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId);

	// get all errors and alarms's settings of login company
	List<ErrorAlarmCondition> getListErrorAlarmCondition(String companyId);

	// update an error/alarm setting
	void addErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain, ErrorAlarmCondition conditionDomain);

	// update an error/alarm setting
	void updateErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain, ErrorAlarmCondition conditionDomain);

	// remove an error/alarm setting
	void removeErrorAlarmWorkRecord(String code);
	
	// get all errors and alarms's settings of login company
	List<ErrorAlarmWorkRecord> getListErAlByListCode(String companyId,List<String> listCode);
	
	List<ErrorAlarmWorkRecord> getAllErAlCompany(String companyId);

	// get all errors and alarms's settings of login company and  Category = Error
    List<ErrorAlarmWorkRecord> getListErAlByListCodeError(String companyId,List<String> listCode);
    
    // get all errors and alarms's settings of login company and  useAtr = true
    List<ErrorAlarmWorkRecord> getAllErAlCompanyAndUseAtr(String companyId, boolean useAtr);
    
    // get all errors and alarms's settings of login company and  useAtr = true
    List<ErrorAlarmWorkRecord> getAllErAlCompanyAndUseAtrV2(String companyId, boolean useAtr);

    /**
     * Key ("Code"), Type (String);
	 * Key ("ErrorDisplayItem"), Type (Integer);
	 * Key ("ErrorAlarmCondition"), Type (ErrorAlarmCondition);
	*/
    List<Map<String, Object>> getErAlByComID(String companyId);
    
    /**
     * 
     * @param companyId
     * @param fixed = 0: user setting, fixed = 1: system setting
     * @return
     */
    List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId, int fixed);
    
}
