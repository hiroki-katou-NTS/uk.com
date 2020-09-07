package nts.uk.ctx.at.record.infra.entity.manageemploymenthours;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Ksrmt36AgrMgtEmpPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "EMP_CD")
    public String employmentCode;

    @Column(name = "LABOR_SYSTEM_ATR")
    public int laborSystemAtr;
}
