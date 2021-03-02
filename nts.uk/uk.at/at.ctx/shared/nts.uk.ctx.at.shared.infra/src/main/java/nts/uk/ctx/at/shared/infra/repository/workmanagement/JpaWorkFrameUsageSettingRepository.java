package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.workframe.WorkFrameUsageSettingRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaWorkFrameUsageSettingRepository extends JpaRepository implements WorkFrameUsageSettingRepository {
    @Override
    public void insert(WorkFrameUsageSetting workFrameUsageSetting) {

    }

    @Override
    public void update(WorkFrameUsageSetting workFrameUsageSetting) {

    }

    @Override
    public WorkFrameUsageSetting getWorkFrameUsageSetting(String cid) {
        return null;
    }
}
