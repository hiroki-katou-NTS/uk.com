package nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbycompany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KrqmtAppApvCmpPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "APP_TYPE")
    public int appType;
}
