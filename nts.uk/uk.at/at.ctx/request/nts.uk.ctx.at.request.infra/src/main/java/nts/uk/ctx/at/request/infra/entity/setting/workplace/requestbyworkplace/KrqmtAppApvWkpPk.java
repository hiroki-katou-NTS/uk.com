package nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbyworkplace;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KrqmtAppApvWkpPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "WKP_ID")
    public String workplaceId;

    @Column(name = "APP_TYPE")
    public int appType;
}
