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
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_ATD")
public class KrcdtDayTsAtd extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiLeavingWorkPK krcdtDaiLeavingWorkPK;

	@Column(name = "WORK_TIMES")
	public Integer workTimes;

	@OneToMany(mappedBy = "daiLeavingWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<KrcdtDayTsAtdStmp> timeLeavingWorks;

	@Override
	protected Object getKey() {
		return this.krcdtDaiLeavingWorkPK;
	}
	
	public TimeLeavingOfDailyPerformance toDomain() {
		return toDomain(this, timeLeavingWorks);
	}

	public static TimeLeavingOfDailyPerformance toDomain(KrcdtDayTsAtd entity, List<KrcdtDayTsAtdStmp> timeLeavingWorks) {
		TimeLeavingOfDailyPerformance domain = new TimeLeavingOfDailyPerformance(entity.krcdtDaiLeavingWorkPK.employeeId,
				new WorkTimes(entity.workTimes),
				KrcdtDayTsAtdStmp.toDomain(timeLeavingWorks.stream()
						.filter(item -> item.krcdtTimeLeavingWorkPK.timeLeavingType == 0).collect(Collectors.toList())),
				entity.krcdtDaiLeavingWorkPK.ymd);
		return domain;
	}

	public static KrcdtDayTsAtd toEntity(TimeLeavingOfDailyPerformance domain) {
		return new KrcdtDayTsAtd(new KrcdtDaiLeavingWorkPK(domain.getEmployeeId(), domain.getYmd()),
				domain.getAttendance().getWorkTimes().v(),
				domain.getAttendance().getTimeLeavingWorks().stream()
						.map(c -> KrcdtDayTsAtdStmp.toEntity(domain.getEmployeeId(), domain.getYmd(), c, 0))
						.collect(Collectors.toList()));
	}
}
