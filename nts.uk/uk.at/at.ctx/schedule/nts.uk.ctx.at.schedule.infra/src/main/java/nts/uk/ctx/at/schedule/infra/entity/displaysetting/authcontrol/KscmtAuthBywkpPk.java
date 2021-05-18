package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAuthBywkpPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Column(name = "CID")
    public String companyId;

    /**
     * ロールID
     */
    @Column(name = "ROLE_ID")
    public String roleId;

    /**
     * 機能NO
     */
    @Column(name = "FUNCTION_NO")
    public int functionNo;
}
