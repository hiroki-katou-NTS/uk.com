package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import java.util.Optional;

public interface TaskFrameUsageSettingRepository {
    Optional<TaskFrameUsageSetting> get(String companyId);
    void add(TaskFrameUsageSetting settings);
    void update(TaskFrameUsageSetting settings);
}
