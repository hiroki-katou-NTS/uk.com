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
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_LEAVING_WORK")
public class KrcdtDaiLeavingWork extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiLeavingWorkPK krcdtDaiLeavingWorkPK;
	
	@Column(name = "WORK_TIMES")
	public BigDecimal workTimes;
	
	@OneToMany(mappedBy="daiLeavingWork", cascade = CascadeType.ALL)
	public List<KrcdtTimeLeavingWork> timeLeavingWorks;
	
	@Override
	protected Object getKey() {
		return this.krcdtDaiLeavingWorkPK;
	}
	
	public TimeLeavingOfDailyPerformance toDomain(){
		TimeLeavingOfDailyPerformance domain = new TimeLeavingOfDailyPerformance(this.krcdtDaiLeavingWorkPK.employeeId,
				new WorkTimes(this.workTimes.intValue()),
				KrcdtTimeLeavingWork.toDomain(timeLeavingWorks),
				this.krcdtDaiLeavingWorkPK.ymd);
		return domain;
	}
}
