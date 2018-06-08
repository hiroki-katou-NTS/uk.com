package nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="KRCMT_INTERIM_SPE_HOLIDAY")
public class KrcmtInterimSpeHoliday extends UkJpaEntity implements Serializable {
	@EmbeddedId
	public KrcmtInterimSpeHolidayPK pk;
	/** 予定実績区分 */
	@Column(name ="SCHE_RECORD_ATR")	
	public int scheRecordAtr;
	/** 特休使用	 */
	@Column(name ="USE_DAYS")
	public Double useDays;
	/** 時間特休使用 */
	@Column(name ="USE_TIMES")
	public int useTimes;
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
