package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 社会保険届作成設定: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtSocInsuNotiSetPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ユーザID
    */
    @Basic(optional = false)
    @Column(name = "USER_ID")
    public String userId;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
}
