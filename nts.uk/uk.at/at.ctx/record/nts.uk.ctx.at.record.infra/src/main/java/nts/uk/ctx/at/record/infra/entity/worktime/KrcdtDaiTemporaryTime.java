package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の臨時出退勤
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
	public BigDecimal workTimes;
	
	@OneToMany(mappedBy="daiTemporaryTime", cascade = CascadeType.ALL)
	public List<KrcdtTimeLeavingWork> timeLeavingWorks;
	
	@Override
	protected Object getKey() {
		return this.krcdtDaiTemporaryTimePK;
	}
	
	public TemporaryTimeOfDailyPerformance toDomain(){
		TemporaryTimeOfDailyPerformance domain = new TemporaryTimeOfDailyPerformance(this.krcdtDaiTemporaryTimePK.employeeId,
				new WorkTimes(this.workTimes.intValue()),
				KrcdtTimeLeavingWork.toDomain(timeLeavingWorks),
				this.krcdtDaiTemporaryTimePK.ymd);
		return domain;
	}

}
