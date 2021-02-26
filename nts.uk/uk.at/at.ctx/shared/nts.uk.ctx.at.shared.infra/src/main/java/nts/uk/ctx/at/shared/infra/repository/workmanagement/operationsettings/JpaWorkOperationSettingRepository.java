package nts.uk.ctx.at.shared.infra.repository.workmanagement.operationsettings;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.operationsettings.KsrmtTaskOperation;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaWorkOperationSettingRepository extends JpaRepository implements WorkOperationSettingRepository {
    @Override
    public Optional<WorkOperationSetting> get(String companyId) {
        return this.queryProxy().find(companyId, KsrmtTaskOperation.class).map(KsrmtTaskOperation::toDomain);
    }
}
