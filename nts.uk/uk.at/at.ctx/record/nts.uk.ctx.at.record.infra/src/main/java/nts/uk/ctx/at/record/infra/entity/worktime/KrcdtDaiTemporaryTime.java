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
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の臨時出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_TEMPORARY_TIME")
public class KrcdtDaiTemporaryTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiTemporaryTimePK krcdtDaiTemporaryTimePK;

	@Column(name = "WORK_TIMES")

	public Integer workTimes;

	@OneToMany(mappedBy = "daiTemporaryTime", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
	public List<KrcdtTimeLeavingWork> timeLeavingWorks;

	@Override
	protected Object getKey() {
		return this.krcdtDaiTemporaryTimePK;
	}

	public TemporaryTimeOfDailyPerformance toDomain() {
		TemporaryTimeOfDailyPerformance domain = new TemporaryTimeOfDailyPerformance(
				this.krcdtDaiTemporaryTimePK.employeeId, new WorkTimes(this.workTimes.intValue()),
				KrcdtTimeLeavingWork.toDomain(timeLeavingWorks.stream()
						.filter(item -> item.krcdtTimeLeavingWorkPK.timeLeavingType == 1).collect(Collectors.toList())),
				this.krcdtDaiTemporaryTimePK.ymd);
		return domain;
	}

	public static KrcdtDaiTemporaryTime toEntity(TemporaryTimeOfDailyPerformance domain) {
		return new KrcdtDaiTemporaryTime(new KrcdtDaiTemporaryTimePK(domain.getEmployeeId(), domain.getYmd()),
				domain.getWorkTimes().v(),
				domain.getTimeLeavingWorks().stream()
						.map(c -> KrcdtTimeLeavingWork.toEntity(domain.getEmployeeId(), domain.getYmd(), c, 1))
						.collect(Collectors.toList()));
	}

}
