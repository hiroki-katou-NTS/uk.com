package nts.uk.screen.at.app.ksc001.d;

import lombok.Getter;
import nts.arc.time.GeneralDate;

import java.util.List;

@Getter
public class ConditionDto {
    private List<String> listEmployeeId;
    /** 異動者. */
    private boolean transfer;

    /** 休職休業者. */
    private boolean leaveOfAbsence;

    /** 短時間勤務者. */
    private boolean shortWorkingHours;

    /** 労働条件変更者. */
    private boolean changedWorkingConditions;

    private GeneralDate startDate;

    private GeneralDate endDate;
}
