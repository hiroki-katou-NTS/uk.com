package nts.uk.screen.at.app.query.kmt.kmt005;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class TaskFrameUsageSettingQuery {
    @Inject
    private TaskOperationSettingRepository operationSettingRepo;

    @Inject
    private TaskFrameUsageSettingRepository repo;

    public List<TaskFrameSettingDto> get() {
        String companyId = AppContexts.user().companyId();
        Optional<TaskOperationSetting> operationSetting = operationSettingRepo.get(companyId);
        if (!operationSetting.isPresent() || operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT001_1");
        return repo.get(companyId).map(i -> i.getFrameSettings().stream().map(setting -> new TaskFrameSettingDto(
                setting.getTaskFrameNo().v(),
                setting.getTaskFrameName().v(),
                setting.getUseAtr().value
        )).collect(Collectors.toList())).orElse(new ArrayList<>());
    }
}
