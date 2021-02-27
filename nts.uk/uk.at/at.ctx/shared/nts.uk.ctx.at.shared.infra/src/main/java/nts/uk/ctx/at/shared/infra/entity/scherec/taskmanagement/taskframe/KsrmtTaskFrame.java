package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskframe;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KSRMT_TASK_FRAME")
@AllArgsConstructor
@NoArgsConstructor
public class KsrmtTaskFrame extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    public String companyId;

    @Column(name = "FRAME1_USE_ATR")
    public int frame1UseAtr;

    @Column(name = "FRAME1_NAME")
    public String frame1Name;

    @Column(name = "FRAME2_USE_ATR")
    public int frame2UseAtr;

    @Column(name = "FRAME2_NAME")
    public String frame2Name;

    @Column(name = "FRAME3_USE_ATR")
    public int frame3UseAtr;

    @Column(name = "FRAME3_NAME")
    public String frame3Name;

    @Column(name = "FRAME4_USE_ATR")
    public int frame4UseAtr;

    @Column(name = "FRAME4_NAME")
    public String frame4Name;

    @Column(name = "FRAME5_USE_ATR")
    public int frame5UseAtr;

    @Column(name = "FRAME5_NAME")
    public String frame5Name;

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public TaskFrameUsageSetting toDomain() {
        List<TaskFrameSetting> settings = new ArrayList<>();
        settings.add(new TaskFrameSetting(1, this.frame1Name, this.frame1UseAtr));
        settings.add(new TaskFrameSetting(2, this.frame2Name, this.frame2UseAtr));
        settings.add(new TaskFrameSetting(3, this.frame3Name, this.frame3UseAtr));
        settings.add(new TaskFrameSetting(4, this.frame4Name, this.frame4UseAtr));
        settings.add(new TaskFrameSetting(5, this.frame5Name, this.frame5UseAtr));
        return new TaskFrameUsageSetting(settings);
    }
}
