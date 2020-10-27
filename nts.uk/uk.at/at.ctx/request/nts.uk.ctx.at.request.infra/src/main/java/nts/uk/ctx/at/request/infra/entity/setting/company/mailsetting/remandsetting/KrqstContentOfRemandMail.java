package nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.remandsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQMT_REMAND_MAIL")
public class KrqstContentOfRemandMail extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrqstContentOfRemandMailPk remandMailPk;
    
    /**
    * 差し戻しメール件名
    */
    @Basic(optional = false)
    @Column(name = "MAIL_TITLE")
    public String mailTitle;
    
    /**
    * 差し戻しメール本文
    */
    @Basic(optional = false)
    @Column(name = "MAIL_BODY")
    public String mailBody;
    
    @Override
    protected Object getKey()
    {
        return remandMailPk;
    }

    public ContentOfRemandMail toDomain() {
        return new ContentOfRemandMail(this.remandMailPk.cid, this.mailTitle, this.mailBody);
    }
    public static KrqstContentOfRemandMail toEntity(ContentOfRemandMail domain) {
        return new KrqstContentOfRemandMail(new KrqstContentOfRemandMailPk(domain.getCid()), domain.getMailTitle().v(), domain.getMailBody().v());
    }

}
