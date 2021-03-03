package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.operationsettings.WorkOperationMethod;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.operationsettings.WorkOperationSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.operationsettings.WorkOperationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.operationsettings.KsrmtTaskOperation;

import javax.ejb.Stateless;
import java.util.Optional;


@Stateless
public class JpaWorkOperationSettingRepository extends JpaRepository implements WorkOperationSettingRepository {
    @Override
    public void insert(WorkOperationSetting workFrameUsageSetting) {
        val entity = KsrmtTaskOperation.toEntity(workFrameUsageSetting);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(WorkOperationSetting workFrameUsageSetting) {
        val entity = KsrmtTaskOperation.toEntity(workFrameUsageSetting);
        this.commandProxy().update(entity);
    }

    @Override
    public Optional<WorkOperationSetting> getWorkOperationSetting(String cid) {

        val entityOpt = this.queryProxy().find(cid, KsrmtTaskOperation.class);
        return entityOpt.map(this::toDomain);

    }

    private WorkOperationSetting toDomain(KsrmtTaskOperation entity) {
        return new WorkOperationSetting(
                EnumAdaptor.valueOf(entity.getOPEATR(), WorkOperationMethod.class)
        );
    }

}
