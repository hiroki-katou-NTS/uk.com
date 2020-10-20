package nts.uk.ctx.at.schedulealarm.app.alarmcheck.command;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleRepository;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.RegisterAlarmCheckConditionService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterAlarmCheckConditionCommandHandler extends CommandHandler<RegisterAlarmCheckConditionCommand> {

    @Inject
    private AlarmCheckConditionScheduleRepository alarmCheckConditionScheduleRepository;

    @Override
    protected void handle(CommandHandlerContext<RegisterAlarmCheckConditionCommand> commandHandlerContext) {
        RegisterAlarmCheckConditionCommand command = commandHandlerContext.getCommand();
        RequireImpl require = new RegisterAlarmCheckConditionCommandHandler.RequireImpl();
        command.getAlarmCheckCondition().stream().forEach(alarmCheckCondition -> {
            AtomTask persist = RegisterAlarmCheckConditionService.register(require, alarmCheckCondition.getCode(), alarmCheckCondition.getMsgLst());
            transaction.execute(() -> {
                persist.run();
            });
        });
    }

    private class RequireImpl implements RegisterAlarmCheckConditionService.Require {

        @Override
        public AlarmCheckConditionSchedule getAlarmCheckCond(AlarmCheckConditionScheduleCode code) {
            String contractCd = AppContexts.user().contractCode();
            String cid = AppContexts.user().companyId();
            return alarmCheckConditionScheduleRepository.get(contractCd, cid, code);
        }

        @Override
        public void updateMessage(AlarmCheckConditionSchedule alarm) {
            String cid = AppContexts.user().companyId();
            alarm.getSubConditions().forEach(item -> System.out.println(item.getMessage().getMessage().v()));
            alarmCheckConditionScheduleRepository.update(cid, alarm);
        }
    }
}
