package nts.uk.file.at.app.export.schedule.personalScheduleByIndividual;

import lombok.experimental.var;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 個人スケジュール表(個人別)を作成する
 */
@Stateless
public class PersonalScheduleByIndividualExportQuery {

    @Inject
    private GetAnAppointmentQuery getAnAppointmentQuery;

    @Inject
    private BasicInformationQuery basicInformationQuery;

    /**
     * 取得する
     *
     * @param employeeId
     * @param employeeCode
     * @param generalDate
     * @param period
     * @param startDate
     * @param isTotalDisplay
     * @return PersonalScheduleDto
     */
    public PersonalScheduleIndividualDataSource get(
            String employeeId,
            String employeeCode,
            GeneralDate generalDate,
            DatePeriod period,
            int startDate,
            boolean isTotalDisplay) {
        var appointmentResult = getAnAppointmentQuery.get(employeeId, period, startDate, isTotalDisplay);
        var basicInfo = basicInformationQuery.get(employeeId, period);
        return new PersonalScheduleIndividualDataSource(
                basicInfo.getCompanyName(),
                basicInfo.getWorkplaceInfo(),
                basicInfo.getDateInformationList(),
                basicInfo.getLegalWorkTimeOfEmployee(),
                appointmentResult.getWorkInforDtoList(),
                appointmentResult.getAgreegateResults()
        );
    }
}
