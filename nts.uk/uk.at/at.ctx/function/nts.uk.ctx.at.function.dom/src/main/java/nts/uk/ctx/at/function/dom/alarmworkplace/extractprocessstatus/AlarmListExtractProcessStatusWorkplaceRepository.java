package nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus;

public interface AlarmListExtractProcessStatusWorkplaceRepository {
	
	//List<AlarmListExtractionInfoWorkplace> getAllAlListExtaProcess(String companyID);

	//Optional<AlarmListExtractionInfoWorkplace> getAlListExtaProcessByID(String extraProcessStatusID);

	//Optional<AlarmListExtractionInfoWorkplace> getAlListExtaProcess(String companyID, GeneralDate startDate, int startTime);

	//Optional<AlarmListExtractionInfoWorkplace> getAlListExtaProcessByStatus(String companyID, GeneralDate startDate, int startTime, int status);

	//Optional<AlarmListExtractionInfoWorkplace> getAlListExtaProcessByEndDate(String companyID, String employeeID);

    void add(AlarmListExtractProcessStatusWorkplace alarmListExtraProcessStatus);

	//void updateAlListExtaProcess(AlarmListExtractionInfoWorkplace alarmListExtraProcessStatus);

	//void deleteAlListExtaProcess(String extraProcessStatusID);

	//boolean isAlListExtaProcessing(String companyId, String employeeId, int status);
}
