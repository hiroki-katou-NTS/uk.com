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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_LEAVING_WORK")
public class KrcdtDaiLeavingWork extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiLeavingWorkPK krcdtDaiLeavingWorkPK;

	@Column(name = "WORK_TIMES")
	public Integer workTimes;

	@OneToMany(mappedBy = "daiLeavingWork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<KrcdtTimeLeavingWork> timeLeavingWorks;

	@Override
	protected Object getKey() {
		return this.krcdtDaiLeavingWorkPK;
	}
	
	public TimeLeavingOfDailyPerformance toDomain() {
		return toDomain(this, timeLeavingWorks);
	}

	public static TimeLeavingOfDailyPerformance toDomain(KrcdtDaiLeavingWork entity, List<KrcdtTimeLeavingWork> timeLeavingWorks) {
		TimeLeavingOfDailyPerformance domain = new TimeLeavingOfDailyPerformance(entity.krcdtDaiLeavingWorkPK.employeeId,
				new WorkTimes(entity.workTimes),
				KrcdtTimeLeavingWork.toDomain(timeLeavingWorks.stream()
						.filter(item -> item.krcdtTimeLeavingWorkPK.timeLeavingType == 0).collect(Collectors.toList())),
				entity.krcdtDaiLeavingWorkPK.ymd);
		return domain;
	}

	public static KrcdtDaiLeavingWork toEntity(TimeLeavingOfDailyPerformance domain) {
		return new KrcdtDaiLeavingWork(new KrcdtDaiLeavingWorkPK(domain.getEmployeeId(), domain.getYmd()),
				domain.getAttendance().getWorkTimes().v(),
				domain.getAttendance().getTimeLeavingWorks().stream()
						.map(c -> KrcdtTimeLeavingWork.toEntity(domain.getEmployeeId(), domain.getYmd(), c, 0))
						.collect(Collectors.toList()));
	}
}
