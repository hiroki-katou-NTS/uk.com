package nts.uk.ctx.at.shared.infra.entity.holidaysetting.interimdata;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * 暫定公休管理データ
 * @author
 *
 */

@Entity
@Table(name ="KSHDT_INTERIM_HDPUB")
public class KshdtInterimHdpub extends ContractUkJpaEntity {

	@Id
	@Column(name = "ID")
	public String ID;
	
	/** 社員ID */
	@Column(name ="SID")
	public String sid;
	/** 対象日 */
	@Column(name ="YMD")
	public GeneralDate ymd;
	/** 作成元区分	 */
	@Column(name ="CREATOR_ATR")
	public int createAtr;
	/** 使用日数 */
	@Column(name ="USE_DAYS")
	public Double usedDays;
	
	@Override
	protected Object getKey() {
		return this.ID;
	}
}
