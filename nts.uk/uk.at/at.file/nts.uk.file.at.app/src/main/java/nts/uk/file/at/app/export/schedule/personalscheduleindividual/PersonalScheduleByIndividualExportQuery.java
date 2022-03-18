package nts.uk.file.at.app.export.schedule.personalscheduleindividual;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.dto.AppointmentDto;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.dto.BasicInformationDto;

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
     * @param period
     * @param startDate
     * @param isTotalDisplay
     * @return PersonalScheduleDto
     */
    public PersonalScheduleIndividualDataSource get(
            DatePeriod period,
            int startDate,
            boolean isTotalDisplay,
            String sid) {
        AppointmentDto appointmentResult = getAnAppointmentQuery.get(period, startDate, isTotalDisplay, sid);
        BasicInformationDto basicInfo = basicInformationQuery.get(isTotalDisplay, appointmentResult.getPeriod(), sid);
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
