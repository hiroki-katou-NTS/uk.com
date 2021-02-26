package nts.uk.ctx.at.shared.app.query.scherec.taskmanagement.operationsetting;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class TaskOperationSettingQuery {
    @Inject
    private TaskOperationSettingRepository repo;

    public TaskOperationSettingDto get() {
        String companyId = AppContexts.user().companyId();
        Optional<TaskOperationSetting> setting = repo.get(companyId);
        if (!setting.isPresent() || setting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (setting.get().getTaskOperationMethod() == TaskOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT001_1");
        return new TaskOperationSettingDto(setting.get().getTaskOperationMethod().value);
    }
}
