package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の臨時出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_TEMPORARY_TIME")
public class KrcdtDaiTemporaryTime extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiTemporaryTimePK krcdtDaiTemporaryTimePK;

	@Column(name = "WORK_TIMES")

	public Integer workTimes;

	@OneToMany(mappedBy = "daiTemporaryTime", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	public List<KrcdtTimeLeavingWork> timeLeavingWorks;

	@Override
	protected Object getKey() {
		return this.krcdtDaiTemporaryTimePK;
	}

	public TemporaryTimeOfDailyPerformance toDomain() {
		return toDomain(this, timeLeavingWorks);
	}
	
	public static TemporaryTimeOfDailyPerformance toDomain(KrcdtDaiTemporaryTime entity, List<KrcdtTimeLeavingWork> timeLeavingWorks) {
		TemporaryTimeOfDailyPerformance domain = new TemporaryTimeOfDailyPerformance(
				entity.krcdtDaiTemporaryTimePK.employeeId, new WorkTimes(entity.workTimes.intValue()),
				KrcdtTimeLeavingWork.toDomain(timeLeavingWorks.stream()
						.filter(item -> item.krcdtTimeLeavingWorkPK.timeLeavingType == 1).collect(Collectors.toList())),
				entity.krcdtDaiTemporaryTimePK.ymd);
		return domain;
	}

	public static KrcdtDaiTemporaryTime toEntity(TemporaryTimeOfDailyPerformance domain) {
		return new KrcdtDaiTemporaryTime(new KrcdtDaiTemporaryTimePK(domain.getEmployeeId(), domain.getYmd()),
				domain.getAttendance().getWorkTimes().v(),
				domain.getAttendance().getTimeLeavingWorks().stream()
						.map(c -> KrcdtTimeLeavingWork.toEntity(domain.getEmployeeId(), domain.getYmd(), c, 1))
						.collect(Collectors.toList()));
	}

}
