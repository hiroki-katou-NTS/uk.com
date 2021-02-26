package nts.uk.ctx.at.shared.app.query.workmanagement.operationsettings;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationMethod;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class WorkOperationSettingQuery {
    @Inject
    private WorkOperationSettingRepository repo;

    public WorkOperationSettingDto get() {
        String companyId = AppContexts.user().companyId();
        Optional<WorkOperationSetting> setting = repo.get(companyId);
        if (!setting.isPresent() || setting.get().getWorkOperationMethod() == WorkOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (setting.get().getWorkOperationMethod() == WorkOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT001_1");
        return new WorkOperationSettingDto(setting.get().getWorkOperationMethod().value);
    }
}
