package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務予定項目状態
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_WORK_SCHEDULE_STATE")
public class KscstWorkScheduleState extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstWorkScheduleStatePK kscstWorkScheduleStatePK; 
	
	@Column(name = "SCHEDULE_EDIT_STATE")
	public int scheduleEditState;
	
	@Override
	protected Object getKey() {
		return this.kscstWorkScheduleStatePK;
	}

}
