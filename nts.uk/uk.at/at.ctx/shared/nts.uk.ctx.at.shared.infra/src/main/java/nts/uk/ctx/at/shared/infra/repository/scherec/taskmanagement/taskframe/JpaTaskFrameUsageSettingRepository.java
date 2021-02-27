package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.taskframe;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskframe.KsrmtTaskFrame;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Comparator;
import java.util.Optional;

@Stateless
public class JpaTaskFrameUsageSettingRepository extends JpaRepository implements TaskFrameUsageSettingRepository {
    @Override
    public Optional<TaskFrameUsageSetting> get(String companyId) {
        return this.queryProxy().find(companyId, KsrmtTaskFrame.class).map(KsrmtTaskFrame::toDomain);
    }

    @Override
    public void add(TaskFrameUsageSetting settings) {
        settings.getFrameSettings().sort(Comparator.comparing(i -> i.getTaskFrameNo().v()));
        KsrmtTaskFrame entity = new KsrmtTaskFrame(
                AppContexts.user().companyId(),
                settings.getFrameSettings().get(0).getUseAtr().value,
                settings.getFrameSettings().get(0).getTaskFrameName().v(),
                settings.getFrameSettings().get(1).getUseAtr().value,
                settings.getFrameSettings().get(1).getTaskFrameName().v(),
                settings.getFrameSettings().get(2).getUseAtr().value,
                settings.getFrameSettings().get(2).getTaskFrameName().v(),
                settings.getFrameSettings().get(3).getUseAtr().value,
                settings.getFrameSettings().get(3).getTaskFrameName().v(),
                settings.getFrameSettings().get(4).getUseAtr().value,
                settings.getFrameSettings().get(4).getTaskFrameName().v()
        );
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(TaskFrameUsageSetting settings) {
        String companyId = AppContexts.user().companyId();
        this.queryProxy().find(companyId, KsrmtTaskFrame.class).ifPresent(entity -> {
            settings.getFrameSettings().sort(Comparator.comparing(i -> i.getTaskFrameNo().v()));
            entity.frame1Name = settings.getFrameSettings().get(0).getTaskFrameName().v();
            entity.frame1UseAtr = settings.getFrameSettings().get(0).getUseAtr().value;
            entity.frame2Name = settings.getFrameSettings().get(1).getTaskFrameName().v();
            entity.frame2UseAtr = settings.getFrameSettings().get(1).getUseAtr().value;
            entity.frame3Name = settings.getFrameSettings().get(2).getTaskFrameName().v();
            entity.frame3UseAtr = settings.getFrameSettings().get(2).getUseAtr().value;
            entity.frame4Name = settings.getFrameSettings().get(3).getTaskFrameName().v();
            entity.frame4UseAtr = settings.getFrameSettings().get(3).getUseAtr().value;
            entity.frame5Name = settings.getFrameSettings().get(4).getTaskFrameName().v();
            entity.frame5UseAtr = settings.getFrameSettings().get(4).getUseAtr().value;
        });
    }
}
