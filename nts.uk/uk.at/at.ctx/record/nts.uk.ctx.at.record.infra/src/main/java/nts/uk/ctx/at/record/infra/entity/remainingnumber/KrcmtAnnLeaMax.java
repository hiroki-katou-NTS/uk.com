package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name ="KRCMT_ANNLEA_MAX")
public class KrcmtAnnLeaMax extends UkJpaEntity{
	
	@Id
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;
	
	@Column(name = "MAX_TIMES")
    public Integer maxTimes;
	
	@Column(name = "USED_TIMES")
    public Integer usedTimes;
	
	@Column(name = "REMAINING_TIMES")
    public Integer remainingTimes;
	
	@Column(name = "MAX_MINUTES")
    public Integer maxMinutes;
	
	@Column(name = "USED_MINUTES")
    public Integer usedMinutes;
	
	@Column(name = "REMAINING_MINUTES")
    public Integer remainingMinutes;
	
	@Override
	protected Object getKey() {
		return employeeId;
	}

}
