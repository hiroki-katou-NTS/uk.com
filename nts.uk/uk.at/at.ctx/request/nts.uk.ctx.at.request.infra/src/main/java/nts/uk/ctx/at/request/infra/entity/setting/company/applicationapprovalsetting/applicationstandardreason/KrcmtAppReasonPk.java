package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationstandardreason;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtAppReasonPk implements Serializable {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "APP_TYPE")
    public int applicationType;

    @Column(name = "REASON_CD")
    public int reasonCode;

    @Column(name = "HD_TYPE")
    public Integer holidayAppType;
}
