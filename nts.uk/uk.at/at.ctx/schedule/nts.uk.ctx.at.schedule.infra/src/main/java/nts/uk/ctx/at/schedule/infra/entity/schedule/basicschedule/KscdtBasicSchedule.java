/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak.KscdtWorkScheduleBreak;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime.KscdtScheTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscdtScheState;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定基本情報 -The Class KscdtBasicSchedule.
 * 
 * @author sonnh1
 *
 * 
 */
@Getter
@Setter
@Entity
@Table(name = "KSCDT_SCHE_BASIC")
public class KscdtBasicSchedule extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The kscdp B schedule PK. */
	@EmbeddedId
	public KscdtBasicSchedulePK kscdpBSchedulePK;

	/** The work type code. */
	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	/** The work time code. */
	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	/** The confirmed atr. */
	@Column(name = "CONFIRMED_ATR")
	public int confirmedAtr;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscdtBasicSchedule", orphanRemoval = true, fetch = FetchType.LAZY)
	public KscdtScheMasterInfo kscdtScheMasterInfo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscdtBasicSchedule", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KscdtWorkScheduleBreak> kscdtScheBreak;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscdtBasicSchedule", orphanRemoval = true, fetch = FetchType.LAZY)
	public KscdtScheTime kscdtScheTime;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscdtBasicSchedule", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KscdtScheState> kscdtScheState;

	@Override
	protected Object getKey() {
		return this.kscdpBSchedulePK;
	}
	
	public KscdtScheMasterInfo getKscdtScheMasterInfo(){
		KscdtScheMasterInfo kscdtScheMasterInfoEntity = this.kscdtScheMasterInfo;
		return kscdtScheMasterInfoEntity;
	}
}
