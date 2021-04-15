package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskmaster;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class KsrmtTaskMasterPk implements Serializable {
    public static final long serialVersionUID = 1L;
    /**
     *
     */
    @Column(name = "CID")
    public String CID;

    /**
     * 作業枠NO: 	作業->	作業枠NO
     */
    @Column(name = "FRAME_NO")
    public int FRAMENO;

    /**
     * 作業コード :作業->コード
     */
    @Column(name = "CD")
    public String CD;

}
