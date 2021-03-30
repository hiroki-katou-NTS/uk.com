package nts.uk.ctx.at.schedule.app.query.shift.table;

import lombok.*;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.HolidayAvailabilityMaxdays;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRuleDateSetting;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;

import java.util.Optional;

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
        val shiftTableRule = (WorkAvailabilityRuleDateSetting) domain.getShiftTableRule().getShiftTableSetting().get();

        return new ShiftTableRuleDto(
                domain.getShiftTableRule().getUsePublicAtr().value
                , domain.getShiftTableRule().getUseWorkAvailabilityAtr().value
                , shiftTableRule.getHolidayMaxDays().v()
                , shiftTableRule.getClosureDate().getClosingDate().getDay()
                , shiftTableRule.getAvailabilityDeadLine().getDay()
                , domain.getShiftTableRule().getAvailabilityAssignMethodList().stream().filter(x -> x == AssignmentMethod.HOLIDAY).findFirst().isPresent() ? 0 : 1
        );
    }
}
