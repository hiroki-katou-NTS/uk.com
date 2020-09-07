package nts.uk.ctx.at.record.infra.entity.managecompanyagreedhours;

import javax.persistence.Column;
import java.io.Serializable;

public class Ksrmt36AgrMgtCmpPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "LABOR_SYSTEM_ATR")
    public int laborSystemAtr;
}