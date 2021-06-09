package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * アラーム社員一覧
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class AlarmEmployeeList {

    /** アラーム抽出情報結果 */
    @Setter
    private List<AlarmExtractInfoResult> alarmExtractInfoResults;

    /** 社員ID */
    private String employeeID;

}
