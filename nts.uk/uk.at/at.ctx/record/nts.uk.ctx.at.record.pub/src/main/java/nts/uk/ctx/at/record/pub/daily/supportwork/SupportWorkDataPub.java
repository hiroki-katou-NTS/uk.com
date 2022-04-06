package nts.uk.ctx.at.record.pub.daily.supportwork;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 応援勤務データを取得するPublish
 */
public interface SupportWorkDataPub {
    /**
     * [1] 勤務する全て応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForWorkingEmployeeByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [2] 勤務する全て応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForWorkingEmployeeByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     * [3] 応援勤務に来た社員の応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForEmployeeComeToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [4] 応援勤務に来た社員の応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForEmployeeComeToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     * [5] 応援勤務に行った社員の応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForEmployeeGoToSupportByWorkplace(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [6] 応援勤務に行った社員の応援データを取得する
     */
    SupportWorkDataExport getSupportWorkDataForEmployeeGoToSupportByWorkLocation(String companyId, DatePeriod period, List<String> workLocationCodes);
}
