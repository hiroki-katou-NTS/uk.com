package nts.uk.ctx.exio.infra.entity.exo.useroutputcnd;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.useroutputcnd.UserOutCndDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 出力条件詳細
*/

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_USER_OUT_CND_DETAIL")
public class OiomtUserOutCndDetail extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtUserOutCndDetailPk userOutCndDetailPk;
    
    /**
    * 条件SQL
    */
    @Basic(optional = false)
    @Column(name = "CND_SQL")
    public String cndSql;
    
    
    
    @Override
    protected Object getKey()
    {
        return userOutCndDetailPk;
    }

    public UserOutCndDetail toDomain() {
        return new UserOutCndDetail(this.cndSql, this.userOutCndDetailPk.cndSetCd, this.userOutCndDetailPk.userId);
    }
    public static OiomtUserOutCndDetail toEntity(UserOutCndDetail domain) {
        return new OiomtUserOutCndDetail(
        		new OiomtUserOutCndDetailPk(domain.getUserId(), domain.getCndSetCd().v()),
        		domain.getCndSql().v());
    }

	public OiomtUserOutCndDetail(OiomtUserOutCndDetailPk userOutCndDetailPk, String cndSql) {
		super();
		this.userOutCndDetailPk = userOutCndDetailPk;
		this.cndSql = cndSql;
		
		
	}
	
    
}
