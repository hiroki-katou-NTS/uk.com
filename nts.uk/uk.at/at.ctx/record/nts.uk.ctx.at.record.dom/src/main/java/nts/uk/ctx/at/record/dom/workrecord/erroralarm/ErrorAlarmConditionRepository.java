package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;

/**
 * @author hieunv
 *
 */
public interface ErrorAlarmConditionRepository {

	// get condition by eralCheckId
	Optional<ErrorAlarmCondition> findConditionByErrorAlamCheckId(String eralCheckId);

	// get message condition by list eralCheckId
	List<ErrorAlarmCondition> findMessageConByListErAlCheckId(List<String> listEralCheckId);
	
	// get condition by list eralCheckId
	List<ErrorAlarmCondition> findConditionByListErrorAlamCheckId(List<String> listEralCheckId);

	// insert an error/alarm setting
	void addErrorAlarmCondition(ErrorAlarmCondition domain);

	// update an error/alarm setting
	void updateErrorAlarmCondition(ErrorAlarmCondition domain);

	// remove an error/alarm setting
	void removeErrorAlarmCondition(List<String> listErAlCheckID);

}
