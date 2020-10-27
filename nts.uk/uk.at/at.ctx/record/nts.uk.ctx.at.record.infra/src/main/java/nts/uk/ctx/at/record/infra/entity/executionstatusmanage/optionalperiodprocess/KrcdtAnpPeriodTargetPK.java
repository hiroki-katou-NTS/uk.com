package nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess;

import java.io.Serializable;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtAnpPeriodTargetPK implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Column(name = "ANY_PERIOD_AGGR_LOG_ID")
	public String aggrId;
	
	@Column(name = "MEMBER_ID")
	public String employeeId;

}
