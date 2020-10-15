package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクルの反映設定
 */
@Getter
public class WorkCycleRefSetting {

    // 勤務サイクルコード
    private final WorkCycleCode workCycleCode;

    // 反映順序
    private final List<WorkCreateMethod> refOrder;

    // スライド日数
    private final int numOfSlideDays;

    // 法定休日の勤務種類
    private final Optional<WorkTypeCode> legalHolidayCd;

    // 法定外休日の勤務種類
    private final Optional<WorkTypeCode> nonStatutoryHolidayCd;

    // 祝日の勤務種類
    private final Optional<WorkTypeCode> holidayCd;

    /**
     * [C-0] 勤務サイクルの反映設定(勤務サイクルコード, 反映順序, スライド日数,法定休日の勤務種類, 法定外休日の勤務種類, 祝日の勤務種類)
     * @param code
     * @param refOrder
     * @param numOfSlideDays
     * @param legalHolidayCd
     * @param nonStatutoryHolidayCd
     * @param holidayCd
     */
    public WorkCycleRefSetting(String code, List<WorkCreateMethod> refOrder, int numOfSlideDays, String legalHolidayCd, String nonStatutoryHolidayCd, String holidayCd) {
        this.workCycleCode = new WorkCycleCode(code);
        this.refOrder = refOrder;
        this.numOfSlideDays = numOfSlideDays;
        this.legalHolidayCd = legalHolidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(legalHolidayCd));
        this.nonStatutoryHolidayCd = nonStatutoryHolidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(nonStatutoryHolidayCd));
        this.holidayCd = holidayCd == null ? Optional.empty() : Optional.of(new WorkTypeCode(holidayCd));
    }

}
