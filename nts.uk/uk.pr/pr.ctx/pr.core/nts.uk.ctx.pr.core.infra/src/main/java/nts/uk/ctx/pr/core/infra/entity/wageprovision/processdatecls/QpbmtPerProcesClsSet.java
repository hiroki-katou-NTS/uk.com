package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSet;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 個人処理区分設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PER_PROCES_CLS_SET")
public class QpbmtPerProcesClsSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPerProcesClsSetPk perProcesClsSetPk;
    
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
    @Column(name = "COMPANY_ID")
    public String companyId;
    
    @Override
    protected Object getKey()
    {
        return perProcesClsSetPk;
    }

    public PerProcessClsSet toDomain() {
        return new PerProcessClsSet(this.perProcesClsSetPk.processCateNo, this.userId, this.companyId);
    }
    public static QpbmtPerProcesClsSet toEntity(PerProcessClsSet domain) {
        return new QpbmtPerProcesClsSet(new QpbmtPerProcesClsSetPk(domain.getProcessCateNo()),domain.getUid(), domain.getCid());
    }

}
