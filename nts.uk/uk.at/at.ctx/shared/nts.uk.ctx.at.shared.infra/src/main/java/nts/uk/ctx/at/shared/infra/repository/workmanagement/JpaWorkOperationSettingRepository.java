package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.operationsettings.WorkOperationSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.operationsettings.WorkOperationSettingRepository;

import javax.ejb.Stateless;
import java.util.Optional;


@Stateless
public class JpaWorkOperationSettingRepository extends JpaRepository implements WorkOperationSettingRepository {
    @Override
    public void insert(WorkOperationSetting workFrameUsageSetting) {

    }

    @Override
    public void update(WorkOperationSetting workFrameUsageSetting) {

    }

    @Override
    public Optional<WorkOperationSetting> getWorkOperationSetting(String cid) {
        return Optional.empty();
    }
}
