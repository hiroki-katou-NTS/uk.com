package nts.uk.screen.at.app.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRuleDateSetting;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTableRuleForCompanyDto {
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

    /**
     * Convert domain to dto
     *
     * @param domain
     * @return
     */
    public static ShiftTableRuleForCompanyDto fromDomain(ShiftTableRuleForOrganization domain) {
        if (domain == null || domain.getShiftTableRule() == null) return null;
        val shiftTableRule = (WorkAvailabilityRuleDateSetting) domain.getShiftTableRule().getShiftTableSetting().orElse(null);
        return new ShiftTableRuleForCompanyDto(
                domain.getShiftTableRule().getUsePublicAtr().value,
                domain.getShiftTableRule().getUseWorkAvailabilityAtr().value,
                shiftTableRule == null ? 0 : shiftTableRule.getHolidayMaxDays().v(),
                shiftTableRule == null ? 0 : shiftTableRule.getClosureDate().getClosingDate().getDay(),
                shiftTableRule == null ? 0 : shiftTableRule.getAvailabilityDeadLine().getDay(),
                domain.getShiftTableRule().getAvailabilityAssignMethodList().stream().anyMatch(x -> x == AssignmentMethod.HOLIDAY) ? 0 : 1
        );
    }
}
