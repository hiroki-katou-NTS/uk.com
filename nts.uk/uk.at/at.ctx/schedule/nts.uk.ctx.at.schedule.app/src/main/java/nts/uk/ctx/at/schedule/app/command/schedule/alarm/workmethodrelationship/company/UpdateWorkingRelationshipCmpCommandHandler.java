package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.company;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.*;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会社の勤務方法の関係性を変更する
 */
@Stateless
public class UpdateWorkingRelationshipCmpCommandHandler extends CommandHandler<UpdateWorkingRelationshipCmpCommand> {

    @Inject
    private WorkMethodRelationshipComRepo relationshipComRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateWorkingRelationshipCmpCommand> context) {

        UpdateWorkingRelationshipCmpCommand command = context.getCommand();

        WorkMethodHoliday workMethodHoliday = new WorkMethodHoliday();
        WorkMethodAttendance workMethodAttendance1 = new WorkMethodAttendance(new WorkTimeCode(command.getWorkTimeCode()));

        //1: get(ログイン会社ID, 対象勤務方法) : Optional<会社の勤務方法の関係性>
        Optional<WorkMethodRelationshipCompany> relationshipCompany = relationshipComRepo.getWithWorkMethod(AppContexts.user().companyId(), command.getTypeWorkMethod() == 0 ? workMethodAttendance1 : workMethodHoliday);

        if (relationshipCompany.isPresent()){
            List<WorkMethod> workMethods = new ArrayList<>();
            if (command.getTypeOfWorkMethods() == WorkMethodClassfication.ATTENDANCE.value){
                workMethods.addAll(command.getWorkMethods().stream().map(x -> new WorkMethodAttendance(new WorkTimeCode(x))).collect(Collectors.toList()));
            }else if (command.getTypeOfWorkMethods() == WorkMethodClassfication.HOLIDAY.value){
                workMethods.add(workMethodHoliday);
            }else {
                workMethods.add(new WorkMethodContinuousWork());
            }
            
            WorkMethodRelationship relationship =
                    WorkMethodRelationship.create(command.getSpecifiedMethod() == 0 ? workMethodAttendance1 : workMethodHoliday,
                            workMethods,
                            EnumAdaptor.valueOf(command.getSpecifiedMethod(),RelationshipSpecifiedMethod.class));
            WorkMethodRelationshipCompany workMethodRelationshipCompany = new WorkMethodRelationshipCompany(relationship);

            //3: update
            relationshipComRepo.update(AppContexts.user().companyId(),workMethodRelationshipCompany);
        }
    }
}
