package nts.uk.ctx.at.schedule.app.command.shift.table;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.*;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <<Command>> 組織のシフト表のルールを登録する
 *
 * @author viet.tx
 */
@Stateless
@Transactional
public class RegisterOrganizationShiftTableRuleCommandHandler extends CommandHandler<RegisterOrganizationShiftTableRuleCommand> {
    @Inject
    private ShiftTableRuleForOrganizationRepo shiftTableRuleOrgRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterOrganizationShiftTableRuleCommand> commandHandlerContext) {
        RegisterOrganizationShiftTableRuleCommand command = commandHandlerContext.getCommand();
        if (command == null) {
            return;
        }

        /** 作る(するしない区分, するしない区分, シフト表の設定, List<勤務希望の指定方法>, Optional<何日前に通知するかの日数>) **/
        Optional<WorkAvailabilityRule> shiftTableSetting = Optional.of(
                new WorkAvailabilityRuleDateSetting(
                        new OneMonth(
                                new DateInMonth(
                                        command.getClosureDate(),
                                        command.getClosureDate() == ClosureDateType.LASTDAY.value
                                )
                        ),
                        new DateInMonth(
                                command.getAvailabilityDeadLine(),
                                command.getAvailabilityDeadLine() == ClosureDateType.LASTDAY.value
                        ),
                        new HolidayAvailabilityMaxdays(command.getHolidayMaxDays())
                )
        );

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

        /** Declare shared variables **/
        String companyId = AppContexts.user().companyId();
        TargetOrganizationUnit targetOrgUnit = EnumAdaptor.valueOf(command.getOrganizationSelection().getUnitSelected(), TargetOrganizationUnit.class);
        String targetId = command.getOrganizationSelection().getOrganizationIdSelected();

        /** get(会社ID、対象組織) **/
        Optional<ShiftTableRuleForOrganization> shiftTableRuleOrg = shiftTableRuleOrgRepo.get(
                companyId,
                TargetOrgIdenInfor.createFromTargetUnit(targetOrgUnit, targetId)
        );

        if (shiftTableRuleOrg.isPresent()) {
            /** 職場を指定して識別情報を作成する(職場ID)**/
            /** 職場グループを指定して識別情報を作成する(職場グループID) **/
            TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.createFromTargetUnit(targetOrgUnit, targetId);
            shiftTableRuleOrgRepo.update(companyId, new ShiftTableRuleForOrganization(targetOrgIdenInfor, shiftTableRule));
        } else {
            /** 職場を指定して識別情報を作成する(職場ID)**/
            /** 職場グループを指定して識別情報を作成する(職場グループID) **/
            TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.createFromTargetUnit(targetOrgUnit, targetId);

            shiftTableRuleOrgRepo.insert(companyId, new ShiftTableRuleForOrganization(targetOrgIdenInfor, shiftTableRule));
        }
    }
}
