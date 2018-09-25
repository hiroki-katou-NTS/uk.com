package nts.uk.ctx.at.function.dom.alarm.extraprocessstatus;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AlarmListExtraProcessStatusRepository {
	
	List<AlarmListExtraProcessStatus> getAllAlListExtaProcess(String companyID);
	
	Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByID(String extraProcessStatusID);
	
	Optional<AlarmListExtraProcessStatus> getAlListExtaProcess(String companyID,GeneralDate startDate,int startTime);
	
	Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByStatus(String companyID,GeneralDate startDate,int startTime,int status);
	
	Optional<AlarmListExtraProcessStatus> getAlListExtaProcessByEndDate(String companyID, String employeeID);
	
	String addAlListExtaProcess (AlarmListExtraProcessStatus alarmListExtraProcessStatus);
	
	void updateAlListExtaProcess (AlarmListExtraProcessStatus alarmListExtraProcessStatus);
	
	void deleteAlListExtaProcess (String extraProcessStatusID);
	
	boolean isAlListExtaProcessing(String companyId, String employeeId,int status);
}
