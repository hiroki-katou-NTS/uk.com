package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * アラーム社員一覧
 * @author viet.txs
 */
@AllArgsConstructor
@Getter
public class AlarmEmployeeList {

    /** アラーム抽出情報結果 */
    private List<AlarmExtractInfoResult> alarmExtractInfoResults;

    /** 社員ID */
    private String employeeID;
}
