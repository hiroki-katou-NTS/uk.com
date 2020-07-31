package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.eclipse.persistence.internal.xr.ValueObject;

/*
    勤務サイクルの勤務情報
 */
@Getter
@Setter
public class WorkCycleInfo extends ValueObject {

    /*
        勤務の日数
     */
    private NumOfWorkingDays days;

    /*
        勤務情報
     */
    private WorkInformation workInformation;

    private DispOrder dispOrder;

    public WorkCycleInfo() {};

    public WorkCycleInfo(int days, String typeCd, String timeCd, int disOrder) {
        this.days = new NumOfWorkingDays(days);
        this.workInformation = new WorkInformation(timeCd, typeCd);
        this.dispOrder = new DispOrder(disOrder);
    }

}
