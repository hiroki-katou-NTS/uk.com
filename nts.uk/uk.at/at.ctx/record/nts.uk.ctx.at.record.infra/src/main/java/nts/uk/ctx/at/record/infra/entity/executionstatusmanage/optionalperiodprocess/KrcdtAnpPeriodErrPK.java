package nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtAnpPeriodErrPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "MEMBER_ID")
	public String memberId;
	
	@Column(name = "PERIOD_ARRG_LOG_ID")
	public String periodArrgLogId;
	
	@Column(name = "RESOURCE_ID")
	public String resourceId;

}
