package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskframe;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RegisterTaskFrameUsageSettingCommandHandler extends CommandHandler<List<TaskFrameSettingCommand>> {
    @Inject
    private TaskFrameUsageSettingRepository repo;

    @Override
    protected void handle(CommandHandlerContext<List<TaskFrameSettingCommand>> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        List<TaskFrameSettingCommand> settings = commandHandlerContext.getCommand();
        TaskFrameUsageSetting domain =  TaskFrameUsageSetting.create(settings.stream().map(i -> new TaskFrameSetting(
                new TaskFrameNo(i.getFrameNo()),
                new TaskFrameName(i.getFrameName()),
                EnumAdaptor.valueOf(i.isUseAtr() ? 1 : 0, UseAtr.class)
        )).collect(Collectors.toList()));
        TaskFrameUsageSetting setting = repo.getWorkFrameUsageSetting(companyId);
        if (setting != null) {
            repo.update(domain);
        } else {
            repo.insert(domain);
        }
    }
}
