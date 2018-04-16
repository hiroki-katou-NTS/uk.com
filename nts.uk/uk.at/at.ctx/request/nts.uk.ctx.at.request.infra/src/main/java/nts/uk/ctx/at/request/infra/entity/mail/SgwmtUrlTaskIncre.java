package nts.uk.ctx.at.request.infra.entity.mail;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.mail.UrlTaskIncre;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SGWMT_URL_TASK_INCRE")
public class SgwmtUrlTaskIncre extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SgwmtUrlTaskIncrePk urlTaskIncrePk;
    
    /**
    * キー
    */
    @Basic(optional = false)
    @Column(name = "TASK_INCRE_KEY")
    public String key;
    
    /**
    * 値
    */
    @Basic(optional = false)
    @Column(name = "TASK_INCRE_VALUE")
    public String value;
    
    @Override
    protected Object getKey()
    {
        return urlTaskIncrePk;
    }

    public UrlTaskIncre toDomain() {
        return UrlTaskIncre.createFromJavaType(this.urlTaskIncrePk.embeddedId, this.urlTaskIncrePk.cid, this.urlTaskIncrePk.taskIncreId, this.key, this.value);
    }
    public static SgwmtUrlTaskIncre toEntity(UrlTaskIncre domain) {
        return new SgwmtUrlTaskIncre(new SgwmtUrlTaskIncrePk(domain.getEmbeddedId(), domain.getCid(), domain.getTaskIncreId()), domain.getKey(), domain.getValue());
    }
}
