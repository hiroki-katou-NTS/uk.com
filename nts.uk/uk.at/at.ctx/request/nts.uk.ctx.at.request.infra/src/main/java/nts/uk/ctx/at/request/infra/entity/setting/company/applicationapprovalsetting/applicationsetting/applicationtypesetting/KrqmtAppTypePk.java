package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppTypePk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "APP_TYPE")
    public int applicationType;
}
