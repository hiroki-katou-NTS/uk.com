package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KsrmtTaskAssignSyaPk {
    // 社員ID
    @Column(name = "SID")
    public String employeeId;

    // 作業枠NO
    @Column(name = "FRAME_NO")
    public int taskFrameNo;

    // 作業コード
    @Column(name = "TASK_CD")
    public String taskCode;
}
