package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の臨時出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_TEMPORARY")
public class KrcdtDayTsTemporary extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiTemporaryTimePK krcdtDaiTemporaryTimePK;

	@OneToMany(mappedBy = "daiTemporaryTime", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	public List<KrcdtDayTsAtdStmp> timeLeavingWorks;

	@Override
	protected Object getKey() {
		return this.krcdtDaiTemporaryTimePK;
	}

	public TemporaryTimeOfDailyPerformance toDomain() {
		return toDomain(this, timeLeavingWorks);
	}
	
	public static TemporaryTimeOfDailyPerformance toDomain(KrcdtDayTsTemporary entity, List<KrcdtDayTsAtdStmp> timeLeavingWorks) {
		TemporaryTimeOfDailyPerformance domain = new TemporaryTimeOfDailyPerformance(
				entity.krcdtDaiTemporaryTimePK.employeeId,
				KrcdtDayTsAtdStmp.toDomain(timeLeavingWorks.stream()
						.filter(item -> item.krcdtTimeLeavingWorkPK.timeLeavingType == 1).collect(Collectors.toList())),
				entity.krcdtDaiTemporaryTimePK.ymd);
		return domain;
	}

	public static KrcdtDayTsTemporary toEntity(TemporaryTimeOfDailyPerformance domain) {
		return new KrcdtDayTsTemporary(new KrcdtDaiTemporaryTimePK(domain.getEmployeeId(), domain.getYmd()),
				domain.getAttendance().getTimeLeavingWorks().stream()
						.map(c -> KrcdtDayTsAtdStmp.toEntity(domain.getEmployeeId(), domain.getYmd(), c, 1))
						.collect(Collectors.toList()));
	}

}
