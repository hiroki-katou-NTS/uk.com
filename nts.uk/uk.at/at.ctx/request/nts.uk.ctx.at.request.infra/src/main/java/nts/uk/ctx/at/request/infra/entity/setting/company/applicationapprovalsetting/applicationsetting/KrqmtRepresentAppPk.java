package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtRepresentAppPk implements Serializable {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "APP_TYPE")
    public int applicationType;

    @Column(name = "OPTION_ATR")
    public Integer optionAtr;
}
