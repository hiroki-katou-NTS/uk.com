package nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailcontenturlsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* : 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqmtUrlEmbeddedPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
}
