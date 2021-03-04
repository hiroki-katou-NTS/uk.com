package nts.uk.ctx.at.shared.infra.entity.workmanagement.worknarrowingdown;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class KsrmtTaskAssignWkpPk implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 職場ID:職場別作業の絞込->職場ID
     */
    @Column(name = "WKP_ID")
    public String WKPID;

    /**
     * 	作業枠NO: 職場別作業の絞込->	作業枠NO
     */
    @Column(name = "FRAME_NO")
    public int FRAMENO;

    /**
     * 	作業コード :職場別作業の絞込->作業一覧
     */
    @Column(name = "TASK_CD")
    public String TASKCD;
}
