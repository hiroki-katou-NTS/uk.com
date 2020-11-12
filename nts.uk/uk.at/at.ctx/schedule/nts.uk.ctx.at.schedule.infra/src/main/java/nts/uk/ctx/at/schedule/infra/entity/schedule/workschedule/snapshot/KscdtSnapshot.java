package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.snapshot;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWork;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/** スナップショット */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KSCDT_SNAPSHOT")
@Getter
public class KscdtSnapshot extends ContractCompanyUkJpaEntity {

	@EmbeddedId
	public KscdtSnapshotPK pk;
	
	/** 勤務種類 **/
	@Column(name = "WKTP_CD")
	public String workTypeCd;
	
	/** 就業時間帯 **/
	@Column(name = "WKTM_CD")
	public String workTimeCd;

	
	/** 所定時間 **/
	@Column(name = "PREDETERMINED_TIME")
	public int predeterminedTime;
										
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public DailySnapshotWork domain() {
		return DailySnapshotWork.of(pk.sid, pk.ymd, 
				SnapShot.of(new WorkInformation(workTypeCd, workTimeCd), 
							new AttendanceTime(predeterminedTime)));
	}
	
	public static KscdtSnapshot create(DailySnapshotWork domain) {
		
		return new KscdtSnapshot(new KscdtSnapshotPK(domain.getSid(), domain.getYmd()), 
								domain.getSnapshot().getWorkInfo().getWorkTypeCode().v(), 
								domain.getSnapshot().getWorkInfo().getWorkTimeCodeNotNull().map(c -> c.v()).orElse(null), 
								domain.getSnapshot().getPredetermineTime().valueAsMinutes());
	}
}
