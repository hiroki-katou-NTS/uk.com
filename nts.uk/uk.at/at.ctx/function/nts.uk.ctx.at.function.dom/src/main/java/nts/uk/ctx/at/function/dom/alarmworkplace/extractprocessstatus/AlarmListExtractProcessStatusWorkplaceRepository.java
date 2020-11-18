package nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus;

public interface AlarmListExtractProcessStatusWorkplaceRepository {
	
	//List<AlarmListExtractInfoWorkplace> getAllAlListExtaProcess(String companyID);

	//Optional<AlarmListExtractInfoWorkplace> getAlListExtaProcessByID(String extraProcessStatusID);

	//Optional<AlarmListExtractInfoWorkplace> getAlListExtaProcess(String companyID, GeneralDate startDate, int startTime);

	//Optional<AlarmListExtractInfoWorkplace> getAlListExtaProcessByStatus(String companyID, GeneralDate startDate, int startTime, int status);

	//Optional<AlarmListExtractInfoWorkplace> getAlListExtaProcessByEndDate(String companyID, String employeeID);

    void add(AlarmListExtractProcessStatusWorkplace alarmListExtraProcessStatus);

	//void updateAlListExtaProcess(AlarmListExtractInfoWorkplace alarmListExtraProcessStatus);

	//void deleteAlListExtaProcess(String extraProcessStatusID);

	//boolean isAlListExtaProcessing(String companyId, String employeeId, int status);
}
