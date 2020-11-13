package nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出処理状況
 * アラームリスト抽出処理状況(職場別)
 *
 * @author Le Huu Dat
 */
@Getter
public class AlarmListExtractProcessStatusWorkplace extends AggregateRoot {

    public AlarmListExtractProcessStatusWorkplace(String id, String companyID, GeneralDate startDate,
                                                  int startTime, String employeeID, GeneralDate endDate, Integer endTime,
                                                  ExtractState status) {
        super();
        this.id = id;
        this.companyID = companyID;
        this.startDate = startDate;
        this.startTime = startTime;
        this.employeeID = Optional.ofNullable(employeeID);
        this.endDate = Optional.ofNullable(endDate);
        this.endTime = Optional.ofNullable(endTime);
        this.status = status;
    }

    /**
     * ID
     */
    private String id;
    /**
     * 会社ID
     */
    private String companyID;
    /**
     * 開始年月日
     */
    private GeneralDate startDate;
    /**
     * 開始時刻
     */
    private int startTime;
    /**
     * 実行社員
     */
    private Optional<String> employeeID;
    /**
     * 終了年月日
     */
    private Optional<GeneralDate> endDate;
    /**
     * 終了時刻
     */
    private Optional<Integer> endTime;
    /**
     * 状態
     */
    private ExtractState status;


    public void setEndDateAndEndTime(GeneralDate endDate, Integer endTime) {
        this.endDate = Optional.ofNullable(endDate);
        this.endTime = Optional.ofNullable(endTime);
    }

    public void setEndDateAndEndTime(GeneralDate endDate, Integer endTime, ExtractState status) {
        this.endDate = Optional.ofNullable(endDate);
        this.endTime = Optional.ofNullable(endTime);
        this.status = status;
    }

    public void setStatus(ExtractState status) {
        this.status = status;
    }

}
