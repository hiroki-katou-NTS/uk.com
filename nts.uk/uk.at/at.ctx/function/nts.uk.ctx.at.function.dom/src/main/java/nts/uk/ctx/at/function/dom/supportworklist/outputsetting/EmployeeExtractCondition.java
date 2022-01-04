package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;

import java.util.ArrayList;
import java.util.List;

/**
 * 社員抽出条件
 */
public enum EmployeeExtractCondition {
    EXTRACT_ALL_WORKING_EMPLOYEES(0, "KHA002_53"), // 勤務した社員すべてを抽出
    EXTRACT_EMPLOYEES_COME_TO_SUPPORT(1, "KHA002_54"), // 応援に来た社員のみを抽出
    EXTRACT_EMPLOYEES_GO_TO_SUPPORT(2, "KHA002_55"); // 応援に行った社員のみを抽出

    public final int value;
    public final String nameId;

    EmployeeExtractCondition(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

    /**
     * [1] 職場別の応援勤務データを取得する
     * @param require
     * @param companyId 会社ID
     * @param period 期間
     * @param workplaceIds 職場リスト
     * @return 応援勤務データ
     */
    public SupportWorkDataImport getSupportWorkDataByWorkplace(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, List<String> workplaceIds) {
        switch (this) {
            case EXTRACT_ALL_WORKING_EMPLOYEES:
                return require.getSupportWorkDataForWorkingEmployeeByWorkplace(companyId, period, workplaceIds);
            case EXTRACT_EMPLOYEES_COME_TO_SUPPORT:
                return require.getSupportWorkDataForEmployeeComeToSupportByWorkplace(companyId, period, workplaceIds);
            case EXTRACT_EMPLOYEES_GO_TO_SUPPORT:
                return require.getSupportWorkDataForEmployeeGoToSupportByWorkplace(companyId, period, workplaceIds);
            default:
                return new SupportWorkDataImport(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
        }
    }

    /**
     * [2] 場所別の応援勤務データを取得する
     * @param require
     * @param companyId 会社ID
     * @param period 期間
     * @param workLocationCodes 場所リスト
     * @return 応援勤務データ
     */
    public SupportWorkDataImport getSupportWorkDataByWorkLocations(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, List<String> workLocationCodes) {
        switch (this) {
            case EXTRACT_ALL_WORKING_EMPLOYEES:
                return require.getSupportWorkDataForWorkingEmployeeByWorkLocation(companyId, period, workLocationCodes);
            case EXTRACT_EMPLOYEES_COME_TO_SUPPORT:
                return require.getSupportWorkDataForEmployeeComeToSupportByWorkLocation(companyId, period, workLocationCodes);
            case EXTRACT_EMPLOYEES_GO_TO_SUPPORT:
                return require.getSupportWorkDataForEmployeeGoToSupportByWorkLocation(companyId, period, workLocationCodes);
            default:
                return new SupportWorkDataImport(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
        }
    }
}
