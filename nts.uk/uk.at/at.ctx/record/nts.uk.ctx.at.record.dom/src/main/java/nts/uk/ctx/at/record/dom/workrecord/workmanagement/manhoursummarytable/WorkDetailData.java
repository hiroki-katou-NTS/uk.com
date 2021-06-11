package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import org.apache.logging.log4j.util.Strings;

/**
 * TP: 作業詳細データ
 */
@AllArgsConstructor
@Getter
public class WorkDetailData {
    /** 社員ID */
    private String employeeId;
    /** 年月日 */
    private GeneralDate date;
    /** 応援勤務枠NO */
    private int supportWorkFrameNo;
    /** 所属職場ID */
    private String affWorkplaceId;
    /** 勤務職場ID */
    private String workplaceId;
    /** 作業コード1 */
    private String workCode1;
    /** 作業コード2 */
    private String workCode2;
    /** 作業コード3 */
    private String workCode3;
    /** 作業コード4 */
    private String workCode4;
    /** 作業コード5 */
    private String workCode5;
    /** 総労働時間 */
    private int totalWorkingHours;

    /**
     * [1] 集計項目をマッピングする
     * @param itemType 集計項目種類
     * @return string
     */
    public String mapSummaryItem(SummaryItemType itemType) {
        String workItem = Strings.EMPTY;
        switch (itemType) {
            case AFFILIATION_WORKPLACE:
                workItem = affWorkplaceId;
                break;
            case WORKPLACE:
                workItem = workplaceId;
                break;
            case EMPLOYEE:
                workItem = employeeId;
                break;
            case TASK1:
                workItem = workCode1;
                break;
            case TASK2:
                workItem = workCode2;
                break;
            case TASK3:
                workItem = workCode3;
                break;
            case TASK4:
                workItem = workCode4;
                break;
            case TASK5:
                workItem = workCode5;
                break;
            default:
                break;
        }
        return workItem;
    }
}
