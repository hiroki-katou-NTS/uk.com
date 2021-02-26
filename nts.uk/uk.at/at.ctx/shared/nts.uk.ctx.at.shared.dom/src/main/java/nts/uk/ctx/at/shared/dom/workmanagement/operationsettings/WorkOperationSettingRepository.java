package nts.uk.ctx.at.shared.dom.workmanagement.operationsettings;

import java.util.Optional;

public interface WorkOperationSettingRepository {
    Optional<WorkOperationSetting> get(String companyId);
}
