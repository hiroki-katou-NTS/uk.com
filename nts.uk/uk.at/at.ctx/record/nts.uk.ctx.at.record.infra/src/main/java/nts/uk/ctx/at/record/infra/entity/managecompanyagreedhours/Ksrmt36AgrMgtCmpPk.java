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

    @Column(name = "CID")
    public String companyID;

    @Column(name = "LABOR_SYSTEM_ATR")
    public int laborSystemAtr;
}