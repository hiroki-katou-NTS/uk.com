package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@Entity
@Table(name="JCMST_MENU_OPERATION")
@AllArgsConstructor
@NoArgsConstructor
public class JcmctMenuOperation extends UkJpaEntity {
	@EmbeddedId
    public JcmctMenuOperationPK jcmctMenuOperationPK;
	
	/* メニューを使用する */
	@Column(name = "USE_MENU")
	public int useMenu;
	
	/* 通知機能を使用する */
	@Column(name = "USE_NOTICE")
	public int useNotice;
	
	/* 承認機能を使用する */
	@Column(name = "USE_APPROVAL")
	public int useApproval;
	
	/* 承認機能を使用する */
	@Column(name = "NO_RANK_ORDER")
	public Integer noRankOrder;
	
	// 会社コード
	@Column(name = "CCD")
	public BigInteger ccd;
	
	@Override
	protected Object getKey() {
		return jcmctMenuOperationPK;
	}
}
