package nts.uk.ctx.at.shared.app.query.scherec.taskmanagement.taskframe;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TaskFrameUsageSettingQuery {
    @Inject
    private TaskFrameUsageSettingRepository repo;

    public List<TaskFrameSettingDto> get() {
        String companyId = AppContexts.user().companyId();
        return repo.get(companyId).map(i -> i.getFrameSettings().stream().map(setting -> new TaskFrameSettingDto(
                setting.getTaskFrameNo().v(),
                setting.getTaskFrameName().v(),
                setting.getUseAtr().value
        )).collect(Collectors.toList())).orElse(new ArrayList<>());
    }
}
