package nts.uk.ctx.at.schedule.app.command.shift.table;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.*;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <<Command>> 会社のシフト表のルールを登録する
 *
 * @author viet.tx
 */
@Stateless
@Transactional
public class RegisterCompanyShiftTableRuleCommandHandler extends CommandHandler<RegisterCompanyShiftTableRuleCommand> {
    @Inject
    private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterCompanyShiftTableRuleCommand> commandHandlerContext) {
        RegisterCompanyShiftTableRuleCommand command = commandHandlerContext.getCommand();
        if (command == null) {
            return;
        }

        String companyId = AppContexts.user().companyId();
        Optional<WorkAvailabilityRule> shiftTableSetting = Optional.of(new WorkAvailabilityRuleDateSetting(
                new OneMonth(new DateInMonth(command.getClosureDate(), EnumAdaptor.valueOf(command.getClosureDate(), ClosureDateType.class) == ClosureDateType.LASTDAY)),
                new DateInMonth(command.getAvailabilityDeadLine(), EnumAdaptor.valueOf(command.getAvailabilityDeadLine(), ClosureDateType.class) == ClosureDateType.LASTDAY),
                new HolidayAvailabilityMaxdays(command.getHolidayMaxDays())
        ));

        List<AssignmentMethod> availabilityAssignMethodList = command.getAvailabilityAssignMethod() == AssignmentMethod.HOLIDAY.value
                ? Collections.singletonList(AssignmentMethod.HOLIDAY)
                : Collections.singletonList(AssignmentMethod.SHIFT);

        ShiftTableRule shiftTableRule = ShiftTableRule.create(
                NotUseAtr.valueOf(command.getUsePublicAtr()),
                NotUseAtr.valueOf(command.getUseWorkAvailabilityAtr()),
                shiftTableSetting,
                availabilityAssignMethodList,
                Optional.of(new FromNoticeDays(command.getUseWorkAvailabilityAtr()))
        );

        // get by CompanyID
        Optional<ShiftTableRuleForCompany> shiftTableRuleCompany = shiftTableRuleForCompanyRepo.get(companyId);
        if (!shiftTableRuleCompany.isPresent()) {
            shiftTableRuleForCompanyRepo.insert(companyId, new ShiftTableRuleForCompany(shiftTableRule));
        } else {
            shiftTableRuleForCompanyRepo.update(companyId, new ShiftTableRuleForCompany(shiftTableRule));
        }
    }
}
