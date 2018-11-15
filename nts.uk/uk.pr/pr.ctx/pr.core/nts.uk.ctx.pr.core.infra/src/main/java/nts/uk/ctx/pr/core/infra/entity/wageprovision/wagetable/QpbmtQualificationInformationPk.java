package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationName;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.*;

/**
 * 資格情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtQualificationInformationPk {

    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "QUALIFICATION_CODE")
    public String qualificationCode;
}

