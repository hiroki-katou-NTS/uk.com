package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private List<AlarmExtractInfoResult> alarmExtractInfoResults;

    /** 社員ID */
    private String employeeID;

    public static void createAlarmEmployeeListBy(List<AlarmEmployeeList> alarmEmployeeList, List<AlarmExtractInfoResult> alarmExtractInfoResults, String sid){
        List<AlarmEmployeeList> alarmEmps = alarmEmployeeList.stream().filter(x -> x.employeeID.equals(sid)).collect(Collectors.toList());
        if (alarmEmps.isEmpty()) {
            alarmEmployeeList.add(new AlarmEmployeeList(
                    alarmExtractInfoResults,
                    sid
            ));
        } else {
            alarmEmployeeList.forEach(x -> {
                if (x.getEmployeeID().equals(sid)) {
                    x.getAlarmExtractInfoResults().addAll(alarmExtractInfoResults);
                }
            });
        }
    }
}
