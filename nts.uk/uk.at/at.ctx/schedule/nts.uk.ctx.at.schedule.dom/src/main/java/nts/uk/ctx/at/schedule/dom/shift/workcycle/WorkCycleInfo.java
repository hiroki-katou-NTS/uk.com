package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

/*
    勤務サイクルの勤務情報
 */
@Getter
@AllArgsConstructor
public class WorkCycleInfo extends ValueObject {

    /*
        勤務の日数
     */
    private final NumOfWorkingDays days;

    /*
        勤務情報
     */
    private final WorkInformation workInformation;

    /**
     *	[C-1] 作る
     * @param 	days
     * @param 	workInformation
     * @return 	勤務サイクルの勤務情報
     */
    public static WorkCycleInfo create(int days, WorkInformation workInformation) {
        if (days < 1 || days > 99) {
            throw new BusinessException("Msg_1689");
        }
        return new WorkCycleInfo(new NumOfWorkingDays(days),workInformation);
    }


    /**
     *	[1] 勤務情報のエラー状態をチェックする
     * @param require
     * @return 	勤務情報のエラー状態
     */
    public ErrorStatusWorkInfo checkError(WorkInformation.Require require, String companyId) {
        return workInformation.checkErrorCondition(require, companyId);
    }

}
