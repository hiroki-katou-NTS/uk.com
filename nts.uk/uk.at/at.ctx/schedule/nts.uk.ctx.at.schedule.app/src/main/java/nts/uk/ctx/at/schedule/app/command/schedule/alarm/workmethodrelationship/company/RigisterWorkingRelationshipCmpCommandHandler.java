package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.company;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.organization.RigisterWorkingRelationshipCommand;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会社の勤務方法の関係性を新規する
 */
@Stateless
public class RigisterWorkingRelationshipCmpCommandHandler extends CommandHandler<RigisterWorkingRelationshipCmpCommand> {

    @Inject
    private WorkMethodRelationshipComRepo relationshipComRepo;

    @Override
    protected void handle(CommandHandlerContext<RigisterWorkingRelationshipCmpCommand> context) {

        RigisterWorkingRelationshipCmpCommand command = context.getCommand();

        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();
        WorkMethodAttendance workMethodAttendance1 = new WorkMethodAttendance(new WorkTimeCode(command.getWorkTimeCode()));

        //1: 存在するか = exists(ログイン会社ID, 対象組織, 対象勤務方法) : boolean
        boolean checkExists = relationshipComRepo.exists(AppContexts.user().companyId(), command.getTypeWorkMethod() == 0 ? workMethodAttendance1 : workMethodHoliday);

        //2: 存在するか == true
        if (checkExists){
            throw new BusinessException("Msg_1785");
        }

        List<WorkMethod> workMethods = new ArrayList<>();
        if (command.getTypeOfWorkMethods() == WorkMethodClassfication.ATTENDANCE.value){
            workMethods.addAll(command.getWorkMethods().stream().map(x -> new WorkMethodAttendance(new WorkTimeCode(x))).collect(Collectors.toList()));
        }else if (command.getTypeOfWorkMethods() == WorkMethodClassfication.HOLIDAY.value){
            workMethods.add(workMethodHoliday);
        }else {
            workMethods.add(new WorkMethodContinuousWork());
        }
        WorkMethodRelationship relationship =
                WorkMethodRelationship.create(command.getTypeWorkMethod() == 0 ? workMethodAttendance1 : workMethodHoliday,
                        workMethods,
                        EnumAdaptor.valueOf(command.getSpecifiedMethod(),RelationshipSpecifiedMethod.class));
        WorkMethodRelationshipCompany newMethodRelationshipCompany = new WorkMethodRelationshipCompany(relationship);

        //3: insert
        relationshipComRepo.insert(AppContexts.user().companyId(),newMethodRelationshipCompany);
    }
}
