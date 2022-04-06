package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;

import java.util.List;
import java.util.Optional;

public interface SupportWorkOutputDataRequire {
    Optional<SupportWorkAggregationSetting> getSetting(String companyId);

    List<DailyAttendanceItemAdapterDto> getDailyAttendanceItems(String companyId, List<Integer> attendanceItemIds);

    List<WorkPlaceInforExport> getWorkplaceInfos(String companyId, List<String> workplaceIds, GeneralDate baseDate);

    /**
     *
     * @param companyId
     * @param period
     * @param workplaceIds
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     *
     * @param companyId
     * @param period
     * @param workLocationCodes
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForWorkingEmployeeByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     *
     * @param companyId
     * @param period
     * @param workplaceIds
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     *
     * @param companyId
     * @param period
     * @param workLocationCodes
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForEmployeeComeToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     *
     * @param companyId
     * @param period
     * @param workplaceIds
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     *
     * @param companyId
     * @param period
     * @param workLocationCodes
     * @return
     */
    SupportWorkDataImport getSupportWorkDataForEmployeeGoToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);
}
