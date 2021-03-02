package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class KsrmtTaskChildPk implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * コード :作業->コード
     */
    @Column(name = "CD")
    public String CD;

    /**
     * 子作業コード :作業->子作業一覧
     */
    @Column(name = "CHILD_CD")
    public String CHILDCD;
}
