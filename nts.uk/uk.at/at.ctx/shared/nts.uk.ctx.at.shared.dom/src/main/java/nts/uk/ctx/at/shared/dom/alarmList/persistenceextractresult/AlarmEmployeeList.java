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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlarmEmployeeList other = (AlarmEmployeeList) obj;
        if (employeeID == null) {
            if (other.employeeID != null) {
                return false;
            }
        } else if (!employeeID.equals(other.employeeID)) {
            return false;
        } else if (!alarmExtractInfoResults.isEmpty() && !((AlarmEmployeeList) obj).getAlarmExtractInfoResults().isEmpty()) {
            for (AlarmExtractInfoResult x : alarmExtractInfoResults) {
                for (AlarmExtractInfoResult y : ((AlarmEmployeeList) obj).getAlarmExtractInfoResults()) {
                    if (x.getAlarmCategory() != y.getAlarmCategory()) {
                        return false;
                    }
                    if (!x.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())) {
                        return false;
                    }
                    if (!x.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())) {
                        return false;
                    }
                    if (x.getAlarmListCheckType().value != y.getAlarmListCheckType().value) {
                        return false;
                    }
                    if (!x.getExtractionResultDetails().isEmpty()) {
                        for (ExtractResultDetail x1 : x.getExtractionResultDetails()) {
                            for (ExtractResultDetail y1 : y.getExtractionResultDetails()) {
                                if (x1.getPeriodDate().getStartDate().isPresent() && x1.getPeriodDate().getStartDate().get().compareTo(y1.getPeriodDate().getStartDate().get()) != 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
