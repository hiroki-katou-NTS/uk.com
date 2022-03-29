package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.StateOfEditMonthly;

/**
 * 任意期間修正の編集状態
 */
@Getter
@AllArgsConstructor
public class AnyPeriodCorrectionEditingState extends AggregateRoot {
    /** 社員ID */
    private String employeeId;

    /** 任意集計枠コード */
    private AnyAggrFrameCode anyPeriodFrameCode;

    /** 対象項目 */
    private int attendanceItemId;

    /** 編集状態 */
    private StateOfEditMonthly state;

    public static AnyPeriodCorrectionEditingState create(String correctingEmployeeId, String targetEmployeeId, String frameCode, Integer attendanceItemId) {
        return new AnyPeriodCorrectionEditingState(
                targetEmployeeId,
                new AnyAggrFrameCode(frameCode),
                attendanceItemId,
                correctingEmployeeId.equals(targetEmployeeId) ? StateOfEditMonthly.HAND_CORRECTION_MYSELF : StateOfEditMonthly.HAND_CORRECTION_OTHER
        );
    }
}
