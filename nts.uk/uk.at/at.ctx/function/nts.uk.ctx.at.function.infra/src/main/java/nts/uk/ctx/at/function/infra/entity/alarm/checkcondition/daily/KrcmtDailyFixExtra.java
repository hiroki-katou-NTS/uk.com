package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAILY_FIX_EXTRA_PK")
public class KrcmtDailyFixExtra extends UkJpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDailyFixExtraPK krcmtDailyFixExtraPK;
	
	@ManyToOne
	@JoinColumn(name="DAILY_ALARM_CON_ID", referencedColumnName="DAILY_ALARM_CON_ID", insertable = false, updatable = false)
	public KrcmtDailyAlarmCondition fixextra;
	
	@Override
	protected Object getKey() {
		return krcmtDailyFixExtraPK;
	}

	public KrcmtDailyFixExtra(KrcmtDailyFixExtraPK krcmtDailyFixExtraPK) {
		super();
		this.krcmtDailyFixExtraPK = krcmtDailyFixExtraPK;
	}

}
