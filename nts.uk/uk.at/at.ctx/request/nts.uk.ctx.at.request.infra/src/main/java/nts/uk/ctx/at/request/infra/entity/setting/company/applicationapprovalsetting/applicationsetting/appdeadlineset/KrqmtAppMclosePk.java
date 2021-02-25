package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

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
public class KrqmtAppMclosePk implements Serializable {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CLOSURE_ID")
    public int closureId;
}
