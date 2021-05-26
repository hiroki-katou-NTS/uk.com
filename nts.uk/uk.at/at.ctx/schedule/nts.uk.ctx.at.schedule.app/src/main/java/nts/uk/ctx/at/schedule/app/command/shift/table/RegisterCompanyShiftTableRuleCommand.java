package nts.uk.ctx.at.schedule.app.command.shift.table;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.FromNoticeDays;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRule;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Optional;

/**
 * @author viet.tx
 */
@Value
public class RegisterCompanyShiftTableRuleCommand {

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
