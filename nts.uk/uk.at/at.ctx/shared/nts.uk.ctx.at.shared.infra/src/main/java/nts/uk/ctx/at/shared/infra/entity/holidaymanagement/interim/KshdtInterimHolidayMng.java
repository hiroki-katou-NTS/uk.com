package nts.uk.ctx.at.shared.infra.entity.holidaymanagement.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnlb
 * 
 *         暫定公休管理データ
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHDT_INTERIM_HDPUB")
@AllArgsConstructor
public class KshdtInterimHolidayMng extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshdtInterimHolidayMngPK pk;

	/** 暫定積立年休管理データID */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngID;

	/** 作成元区分 */
	@Column(name = "CREATOR_ATR")
	public int creatorAtr;
	/** 使用日数 */
	@Column(name = "USED_DAYS")
	public double useDays;

	@Override
	protected Object getKey() {
		return pk;
	}

}
