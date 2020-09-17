package nts.uk.ctx.at.record.infra.entity.managecompanyagreedhours;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Ksrmt36AgrMgtCmpPk implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 会社ID
     */
    @Column(name = "CID")
    public String companyID;
    /**
     * ３６協定労働制
     * 0：一般労働制
     * 1：変形労働時間制
     * 会社３６協定時間
     */
    @Column(name = "LABOR_SYSTEM_ATR")
    public int laborSystemAtr;
}