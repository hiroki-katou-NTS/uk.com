package nts.uk.screen.at.app.query.kmt.kmt011;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TaskOperationSettingQuery {
    @Inject
    private TaskOperationSettingRepository repo;

    public TaskOperationSettingDto get() {
        String companyId = AppContexts.user().companyId();
        Optional<TaskOperationSetting> setting = repo.getTasksOperationSetting(companyId);
        if (!setting.isPresent() || setting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (setting.get().getTaskOperationMethod() == TaskOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT011_1");
        return new TaskOperationSettingDto(setting.get().getTaskOperationMethod().value);
    }
}
