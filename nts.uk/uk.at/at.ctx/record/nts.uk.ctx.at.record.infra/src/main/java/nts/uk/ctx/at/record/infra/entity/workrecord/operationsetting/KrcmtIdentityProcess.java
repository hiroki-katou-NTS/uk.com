package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.YourselfConfirmError;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 本人確認処理の利用設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_SELF_CHECK_SET")
public class KrcmtIdentityProcess extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtIdentityProcessPk identityProcessPk;
    
    /**
    * 日の本人確認を利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_DAILY_SELF_CHECK")
    public int useDailySelfCk;
    
    /**
    * 月の本人確認を利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_MONTHLY_SELF_CHECK")
    public int useMonthSelfCK;
    
    /**
    * エラーがある場合の本人確認
    */
    @Basic(optional = true)
    @Column(name = "YOURSELF_CONFIRM_ERROR")
    public Integer yourselfConfirmError;
    
    @Override
    protected Object getKey()
    {
        return identityProcessPk;
    }

    public IdentityProcess toDomain() {
        return new IdentityProcess(this.identityProcessPk.cid, this.useDailySelfCk, 
					        		this.useMonthSelfCK, 
					        		this.yourselfConfirmError == null ? null : EnumAdaptor.valueOf(this.yourselfConfirmError, YourselfConfirmError.class));
    }
    public static KrcmtIdentityProcess toEntity(IdentityProcess domain) {
        return new KrcmtIdentityProcess(new KrcmtIdentityProcessPk(domain.getCid()), 
						        		domain.getUseDailySelfCk(),
						        		domain.getUseMonthSelfCK(), 
						        		domain.getYourselfConfirmError() == null ? null : domain.getYourselfConfirmError().value);
    }

}
