package nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailcontenturlsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQMT_URL_EMBEDDED")
public class KrqmtUrlEmbedded extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqmtUrlEmbeddedPk urlEmbeddedPk;
    
    /**
    * URL埋込
    */
    @Basic(optional = false)
    @Column(name = "URL_EMBEDDED")
    public int urlEmbedded;
    
    @Override
    protected Object getKey()
    {
        return urlEmbeddedPk;
    }

    public UrlEmbedded toDomain() {
        return UrlEmbedded.createFromJavaType(this.urlEmbeddedPk.cid, this.urlEmbedded);
    }
    public static KrqmtUrlEmbedded toEntity(UrlEmbedded domain) {
        return new KrqmtUrlEmbedded(new KrqmtUrlEmbeddedPk(domain.getCid()), domain.getUrlEmbedded().value);
    }

}
