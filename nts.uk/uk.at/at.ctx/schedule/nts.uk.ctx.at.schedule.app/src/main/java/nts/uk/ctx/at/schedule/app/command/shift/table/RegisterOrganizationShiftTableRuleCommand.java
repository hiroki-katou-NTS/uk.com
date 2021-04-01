package nts.uk.ctx.at.schedule.app.command.shift.table;

import lombok.Value;

import java.util.List;

/**
 * @author viet.tx
 */
@Value
public class RegisterOrganizationShiftTableRuleCommand {

    /** 組織選択リスト **/
    private List<OrganizationSelectionDto> organizationSelectionList;

    /** 公開機能の利用区分 */
    private int usePublicAtr;

    /** 勤務希望の利用区分 */
    private int useWorkAvailabilityAtr;

    /** 希望休日の上限日数入力 */
    private int holidayMaxDays;

    /** 締め日 */
    private int closureDate;

    /** 締切日 */
    private int availabilityDeadLine;

    /** 入力方法の利用区分 */
    private int availabilityAssignMethod;
}

