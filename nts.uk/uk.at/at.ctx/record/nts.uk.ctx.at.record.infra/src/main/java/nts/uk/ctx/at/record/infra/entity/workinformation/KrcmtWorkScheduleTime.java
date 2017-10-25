package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報.勤務予定時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WORK_SCHEDULE_TIME")
public class KrcmtWorkScheduleTime extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtWorkScheduleTimePK krcmtWorkScheduleTimePK;
	
	@Column(name = "ATTENDANCE")
	public BigDecimal attendance;

	@Column(name = "LEAVE_WORK")
	public BigDecimal leaveWork;
	
	@Override
	protected Object getKey() {
		return this.krcmtWorkScheduleTimePK;
	}

}
