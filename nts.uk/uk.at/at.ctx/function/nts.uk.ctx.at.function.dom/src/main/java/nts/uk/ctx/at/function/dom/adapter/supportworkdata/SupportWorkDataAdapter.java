package nts.uk.ctx.at.function.dom.adapter.supportworkdata;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 応援勤務データを取得するAdapter
 */
public interface SupportWorkDataAdapter {
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
