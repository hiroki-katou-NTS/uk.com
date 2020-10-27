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
@Table(name = "KRQST_URL_EMBEDDED")
public class KrqstUrlEmbedded extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqstUrlEmbeddedPk urlEmbeddedPk;
    
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
    public static KrqstUrlEmbedded toEntity(UrlEmbedded domain) {
        return new KrqstUrlEmbedded(new KrqstUrlEmbeddedPk(domain.getCid()), domain.getUrlEmbedded().value);
    }

}
