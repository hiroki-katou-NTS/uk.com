package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.operationsetting;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.operationsetting.KsrmtTaskOperation;

import javax.ejb.Stateless;
import java.util.Optional;


@Stateless
public class JpaTaskOperationSettingRepository extends JpaRepository implements TaskOperationSettingRepository {
    @Override
    public void insert(TaskOperationSetting setting) {
        val entity = KsrmtTaskOperation.toEntity(setting);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(TaskOperationSetting setting) {
        val entity = KsrmtTaskOperation.toEntity(setting);
        this.commandProxy().update(entity);
    }

    @Override
    public Optional<TaskOperationSetting> getTasksOperationSetting(String cid) {

        val entityOpt = this.queryProxy().find(cid, KsrmtTaskOperation.class);
        return entityOpt.map(this::toDomain);

    }

    private TaskOperationSetting toDomain(KsrmtTaskOperation entity) {
        return new TaskOperationSetting(
                EnumAdaptor.valueOf(entity.getOPEATR(), TaskOperationMethod.class)
        );
    }

}
