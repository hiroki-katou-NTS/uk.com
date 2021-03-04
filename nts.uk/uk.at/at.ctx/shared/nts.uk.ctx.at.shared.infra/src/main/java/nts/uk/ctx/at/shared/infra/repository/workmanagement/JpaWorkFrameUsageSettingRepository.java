package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameName;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.workframe.WorkFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workframe.KsrmtTaskFrame;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaWorkFrameUsageSettingRepository extends JpaRepository implements WorkFrameUsageSettingRepository {
    @Override
    public void insert(WorkFrameUsageSetting workFrameUsageSetting) {
        val entity = KsrmtTaskFrame.toEntity(workFrameUsageSetting);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(WorkFrameUsageSetting workFrameUsageSetting) {
        val entity = KsrmtTaskFrame.toEntity(workFrameUsageSetting);
        this.commandProxy().update(entity);
    }

    @Override
    public WorkFrameUsageSetting getWorkFrameUsageSetting(String cid) {
        val entity = this.queryProxy().find(cid, KsrmtTaskFrame.class);
        return entity.map(this::toDomain).orElse(null);
    }

    private WorkFrameUsageSetting toDomain(KsrmtTaskFrame entity) {
        List<WorkFrameSetting> frameSettingList = new ArrayList<>();

        WorkFrameSetting frameSetting01 = new WorkFrameSetting(
                new TaskFrameNo(1),
                new WorkFrameName(entity.FRAME1NAME),
                EnumAdaptor.valueOf(entity.FRAME1USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting01);
        WorkFrameSetting frameSetting02 = new WorkFrameSetting(
                new TaskFrameNo(2),
                new WorkFrameName(entity.FRAME2NAME),
                EnumAdaptor.valueOf(entity.FRAME2USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting02);
        WorkFrameSetting frameSetting03 = new WorkFrameSetting(
                new TaskFrameNo(3),
                new WorkFrameName(entity.FRAME3NAME),
                EnumAdaptor.valueOf(entity.FRAME3USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting03);
        WorkFrameSetting frameSetting04 = new WorkFrameSetting(
                new TaskFrameNo(4),
                new WorkFrameName(entity.FRAME4NAME),
                EnumAdaptor.valueOf(entity.FRAME4USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting04);
        WorkFrameSetting frameSetting05 = new WorkFrameSetting(
                new TaskFrameNo(5),
                new WorkFrameName(entity.FRAME5NAME),
                EnumAdaptor.valueOf(entity.FRAME5USEATR, UseAtr.class)
        );
        frameSettingList.add(frameSetting05);
        return new WorkFrameUsageSetting(
                frameSettingList
        );
    }

}
