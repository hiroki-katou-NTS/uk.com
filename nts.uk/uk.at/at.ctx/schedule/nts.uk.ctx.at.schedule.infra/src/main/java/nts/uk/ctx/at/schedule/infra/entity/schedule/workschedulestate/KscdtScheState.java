package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定項目状態
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCHE_STATE")
public class KscdtScheState extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscdtScheStatePK kscdtScheStatePK; 
	
	@Column(name = "SCHE_EDIT_STATE")
	public int scheduleEditState;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSCDT_SCHE_BASIC.SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "KSCDT_SCHE_BASIC.YMD", insertable = false, updatable = false) })
	public KscdtBasicSchedule kscdtBasicSchedule;
	
	@Override
	protected Object getKey() {
		return this.kscdtScheStatePK;
	}

	public KscdtScheState(KscdtScheStatePK kscdtScheStatePK, int scheduleEditState) {
		super();
		this.kscdtScheStatePK = kscdtScheStatePK;
		this.scheduleEditState = scheduleEditState;
	}
}
