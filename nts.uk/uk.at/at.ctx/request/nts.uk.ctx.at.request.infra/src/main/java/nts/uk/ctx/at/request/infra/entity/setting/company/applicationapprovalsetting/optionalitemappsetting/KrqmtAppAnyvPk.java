package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.optionalitemappsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KrqmtAppAnyvPk {
    @Column(name = "CID")
    private String companyId;

    @Column(name = "ANYV_CD")
    private String anyItemCode;
}
