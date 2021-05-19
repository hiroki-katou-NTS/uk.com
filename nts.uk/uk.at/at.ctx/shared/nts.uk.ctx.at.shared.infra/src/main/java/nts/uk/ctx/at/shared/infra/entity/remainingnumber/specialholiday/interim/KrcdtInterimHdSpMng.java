package nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 暫定特別休暇管理データ - old name(some doc still use)
 * 暫定特別休暇データ - new name
 * @author
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KSHDT_INTERIM_HDSP")
public class KrcdtInterimHdSpMng extends ContractUkJpaEntity implements Serializable {
	
	@EmbeddedId
	public KrcmtInterimSpeHolidayPK pk;

	/**残数管理データID	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;

	/** 作成元区分	 */
	@Column(name ="CREATOR_ATR")
	public int createAtr;

	/** 管理単位区分 */
	@Column(name ="MNG_ATR")
	public int mngAtr;
	
	/** 使用日数 */
	@Column(name ="USED_DAYS")
	public Double usedDays;
	
	/** 使用時間 */
	@Column(name ="USED_TIME")
	public Integer usedTime;
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		return pk;
	}

}
