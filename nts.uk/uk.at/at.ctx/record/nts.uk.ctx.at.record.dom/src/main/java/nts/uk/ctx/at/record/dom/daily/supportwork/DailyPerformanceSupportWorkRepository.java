package nts.uk.ctx.at.record.dom.daily.supportwork;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.export.SupportWorkData;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;

import java.util.List;

/**
 * 日別実績の応援作業別勤怠時間帯Repository
 */
public interface DailyPerformanceSupportWorkRepository {
    /**
     * [1]  作業詳細データを取得する
     * @param employeeIds 社員リスト
     * @param workplaceIds 職場リスト
     * @param period 期間
     * @return List<作業詳細データ>
     */
    List<WorkDetailData> getWorkDetailData(List<String> employeeIds, List<String> workplaceIds, DatePeriod period);

    /**
     * [2]  削除する
     * @param employeeId 社員ID
     * @param date 年月日
     * @param supportWorkFrameNo 応援勤務枠NO
     */
    void delete(String employeeId, GeneralDate date, int supportWorkFrameNo);

    /**
     * [3]  勤務する全て応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workplaceIds 職場リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForWorkingEmployeesByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [4]  勤務する全て応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workLocationCodes 勤務場所リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForWorkingEmployeesByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     * [5]  応援勤務に来た社員の応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workplaceIds 職場リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [6]  応援勤務に来た社員の応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workLocationCodes 勤務場所リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForEmployeesComeToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     * [7]  応援勤務に行った社員の応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workplaceIds 職場リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkplaces(String companyId, DatePeriod period, List<String> workplaceIds);

    /**
     * [8]  応援勤務に行った社員の応援データを取得する
     * @param companyId 会社ID
     * @param period 期間
     * @param workLocationCodes 勤務場所リスト
     * @return 応援勤務データ
     */
    SupportWorkData getAllSupportWorkDataForEmployeesGoToSupportByWorkLocations(String companyId, DatePeriod period, List<String> workLocationCodes);

    /**
     * [9]  取得する
     * @param employeeId
     * @param dates
     * @return
     */
    List<OuenWorkTimeSheetOfDaily> get(String employeeId, List<GeneralDate> dates);
}
