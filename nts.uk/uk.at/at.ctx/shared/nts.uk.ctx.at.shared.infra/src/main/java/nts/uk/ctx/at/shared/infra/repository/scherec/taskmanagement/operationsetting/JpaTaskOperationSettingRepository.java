package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.operationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.operationsetting.KsrmtTaskOperation;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaTaskOperationSettingRepository extends JpaRepository implements TaskOperationSettingRepository {
    @Override
    public Optional<TaskOperationSetting> get(String companyId) {
        return this.queryProxy().find(companyId, KsrmtTaskOperation.class).map(KsrmtTaskOperation::toDomain);
    }

    @Override
    public void insert(TaskOperationSetting setting) {
        KsrmtTaskOperation entity = new KsrmtTaskOperation(
                AppContexts.user().companyId(),
                setting.getTaskOperationMethod().value
        );
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(TaskOperationSetting setting) {
        String companyId = AppContexts.user().companyId();
        this.queryProxy().find(companyId, KsrmtTaskOperation.class).ifPresent(entity -> {
            entity.useATR = setting.getTaskOperationMethod().value;
            this.commandProxy().update(entity);
        });
    }
}
