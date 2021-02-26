package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting;

import java.util.Optional;

public interface TaskOperationSettingRepository {
    Optional<TaskOperationSetting> get(String companyId);
}
