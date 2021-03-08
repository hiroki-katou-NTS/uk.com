package nts.uk.ctx.at.shared.infra.repository.taskmanagement;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe.TaskFrameName;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskframe.KsrmtTaskFrame;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaTaskFrameUsageSettingRepository extends JpaRepository implements TaskFrameUsageSettingRepository {
    @Override
    public void insert(TaskFrameUsageSetting taskFrameUsageSetting) {
        val entity = KsrmtTaskFrame.toEntity(taskFrameUsageSetting);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(TaskFrameUsageSetting taskFrameUsageSetting) {
        val entity = KsrmtTaskFrame.toEntity(taskFrameUsageSetting);
        this.commandProxy().update(entity);
    }

    @Override
    public TaskFrameUsageSetting getWorkFrameUsageSetting(String cid) {
        val entity = this.queryProxy().find(cid, KsrmtTaskFrame.class);
        return entity.map(this::toDomain).orElse(null);
    }

    private TaskFrameUsageSetting toDomain(KsrmtTaskFrame entity) {
        List<TaskFrameSetting> frameSettingList = new ArrayList<>();

        TaskFrameSetting frameSetting01 = new TaskFrameSetting(
                new TaskFrameNo(1),
                new TaskFrameName(entity.FRAME1NAME),
                EnumAdaptor.valueOf(entity.FRAME1USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting01);
        TaskFrameSetting frameSetting02 = new TaskFrameSetting(
                new TaskFrameNo(2),
                new TaskFrameName(entity.FRAME2NAME),
                EnumAdaptor.valueOf(entity.FRAME2USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting02);
        TaskFrameSetting frameSetting03 = new TaskFrameSetting(
                new TaskFrameNo(3),
                new TaskFrameName(entity.FRAME3NAME),
                EnumAdaptor.valueOf(entity.FRAME3USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting03);
        TaskFrameSetting frameSetting04 = new TaskFrameSetting(
                new TaskFrameNo(4),
                new TaskFrameName(entity.FRAME4NAME),
                EnumAdaptor.valueOf(entity.FRAME4USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting04);
        TaskFrameSetting frameSetting05 = new TaskFrameSetting(
                new TaskFrameNo(5),
                new TaskFrameName(entity.FRAME5NAME),
                EnumAdaptor.valueOf(entity.FRAME5USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting05);
        return new TaskFrameUsageSetting(
                frameSettingList
        );
    }

}
