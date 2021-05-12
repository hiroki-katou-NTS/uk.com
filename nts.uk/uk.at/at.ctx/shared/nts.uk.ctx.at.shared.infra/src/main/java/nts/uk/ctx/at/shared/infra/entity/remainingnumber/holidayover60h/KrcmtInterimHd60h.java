package nts.uk.ctx.at.shared.infra.entity.remainingnumber.holidayover60h;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnlb
 *
 *         暫定60H超休管理データ
 */
@Entity
@Table(name = "KSHDT_INTERIM_HD60H")
public class KrcmtInterimHd60h extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KrcmtInterimHd60hPK pk;

	/**残数管理データID	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;
	
	/** 作成元区分	 */
	@Column(name ="CREATOR_ATR")
	public int createAtr;
	
	/** 使用時間 */
	@Column(name = "USED_TIME")
	public Integer usedTime;

	@Override
	protected Object getKey() {
		return pk;
	}

}
