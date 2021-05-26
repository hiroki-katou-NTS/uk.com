package nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定代休管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHDT_INTERIM_HDCOM")
public class KrcmtInterimDayOffMng extends ContractUkJpaEntity implements Serializable{
	
	@EmbeddedId
	public KrcmtInterimDayOffMngPK pk;

	/**残数管理データID	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;
	
	/** 作成元区分	 */
	@Column(name ="CREATOR_ATR")
	public int createAtr;
	
	/** 必要日数 */
	@Column(name = "REQUIRED_DAYS")
	public Double requiredDays;
	
	/**	必要時間数 */
	@Column(name = "REQUIRED_TIMES")	
	public int requiredTimes;
	
	/**	未相殺日数 */
	@Column(name = "UNOFFSET_DAYS")
	public Double unOffsetDays;
	
	/**	未相殺時間数 */
	@Column(name = "UNOFFSET_TIMES")
	public int unOffSetTimes;

	private static final long serialVersionUID = 1L;

@Override
	protected Object getKey() {
		return pk;
	}

}
