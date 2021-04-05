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

        /** TH シフト表のルール．勤務希望運用区分 = する、vì 「シフト表のルール．シフト表の設定」ko empty nên chắc chắn có thể mapping */
        if (domain.getShiftTableRule().getUseWorkAvailabilityAtr().value == 1) {
            val shiftTableRule = (WorkAvailabilityRuleDateSetting) domain.getShiftTableRule().getShiftTableSetting().get();
            return new ShiftTableRuleForCompanyDto(
                    domain.getShiftTableRule().getUsePublicAtr().value
                    , domain.getShiftTableRule().getUseWorkAvailabilityAtr().value
                    , shiftTableRule.getHolidayMaxDays().v()
                    , shiftTableRule.getClosureDate().getClosingDate().getDay()
                    , shiftTableRule.getAvailabilityDeadLine().getDay()
                    , domain.getShiftTableRule().getAvailabilityAssignMethodList().stream().filter(x -> x == AssignmentMethod.HOLIDAY).findFirst().isPresent() ? 0 : 1
            );
        }

        return new ShiftTableRuleForCompanyDto(
                domain.getShiftTableRule().getUsePublicAtr().value
                , domain.getShiftTableRule().getUseWorkAvailabilityAtr().value
                , 0
                , 0
                , 0
                , domain.getShiftTableRule().getAvailabilityAssignMethodList().stream().filter(x -> x == AssignmentMethod.HOLIDAY).findFirst().isPresent() ? 0 : 1
        );
    }
}
