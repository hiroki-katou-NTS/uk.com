package nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KRCMT_INTERIM_SPE_HOLIDAY")
public class KrcmtInterimSpeHoliday extends ContractUkJpaEntity implements Serializable {
	@EmbeddedId
	public KrcmtInterimSpeHolidayPK pk;

	/** 予定実績区分 */
	@Column(name ="MNG_ATR")	
	public int mngAtr;
	/** 特休使用	 */
	@Column(name ="USE_DAYS")
	public double useDays;
	/** 時間特休使用 */
	@Column(name ="USE_TIMES")
	public Integer useTimes;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
