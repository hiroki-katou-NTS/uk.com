package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.operationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.operationsetting.KsrmtTaskOperation;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaTaskOperationSettingRepository extends JpaRepository implements TaskOperationSettingRepository {
    @Override
    public Optional<TaskOperationSetting> get(String companyId) {
        return this.queryProxy().find(companyId, KsrmtTaskOperation.class).map(KsrmtTaskOperation::toDomain);
    }
}
