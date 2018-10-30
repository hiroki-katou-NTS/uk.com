package nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayout;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 明細書レイアウト
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SPEC_LAYOUT")
public class QpbmtSpecLayout extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSpecLayoutPk specLayoutPk;
    
    /**
    * 明細書名称
    */
    @Basic(optional = false)
    @Column(name = "SPEC_NAME")
    public String specName;
    
    @Override
    protected Object getKey()
    {
        return specLayoutPk;
    }

    public SpecificationLayout toDomain() {
        return new SpecificationLayout(this.specLayoutPk.cid, this.specLayoutPk.specCd, this.specName);
    }
    public static QpbmtSpecLayout toEntity(SpecificationLayout domain) {
        return new QpbmtSpecLayout(new QpbmtSpecLayoutPk(domain.getCid(),domain.getSpecCode().v()),domain.getSpecName().v());
    }

}
