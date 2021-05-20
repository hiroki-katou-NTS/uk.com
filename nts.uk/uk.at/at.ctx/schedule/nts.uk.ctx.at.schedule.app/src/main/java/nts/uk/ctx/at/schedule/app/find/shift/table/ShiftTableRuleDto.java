package nts.uk.ctx.at.schedule.app.find.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRuleDateSetting;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;

/**
 * @author viet.tx
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftTableRuleDto {
    /**
     * 公開機能の利用区分
     */
    private int usePublicAtr;

    /**
     * 勤務希望の利用区分
     */
    private int useWorkAvailabilityAtr;

    /**
     * 希望休日の上限日数入力
     */
    private int holidayMaxDays;

    /**
     * 締め日
     */
    private int closureDate;

    /**
     * 締切日
     */
    private int availabilityDeadLine;

    /**
     * 入力方法の利用区分
     */
    private int availabilityAssignMethod;

    public static ShiftTableRuleDto fromDomain(ShiftTableRuleForCompany domain) {
        if (domain == null || domain.getShiftTableRule() == null) return null;
        val shiftTableSetting = (WorkAvailabilityRuleDateSetting) domain.getShiftTableRule().getShiftTableSetting().orElse(null);

        return new ShiftTableRuleDto(
                domain.getShiftTableRule().getUsePublicAtr().value,
                domain.getShiftTableRule().getUseWorkAvailabilityAtr().value,
                shiftTableSetting == null ? 0 : shiftTableSetting.getHolidayMaxDays().v(),
                shiftTableSetting == null ? 0 : shiftTableSetting.getClosureDate().getClosingDate().getDay(),
                shiftTableSetting == null ? 0 : shiftTableSetting.getAvailabilityDeadLine().getDay(),
                domain.getShiftTableRule().getAvailabilityAssignMethodList().stream().anyMatch(x -> x == AssignmentMethod.HOLIDAY) ? 0 : 1
        );
    }
}
