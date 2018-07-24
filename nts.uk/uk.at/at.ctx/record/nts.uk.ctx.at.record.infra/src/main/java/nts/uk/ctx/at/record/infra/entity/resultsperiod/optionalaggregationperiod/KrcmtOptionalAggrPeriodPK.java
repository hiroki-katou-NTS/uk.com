package nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtOptionalAggrPeriodPK implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "AGGR_FRAME_CODE")
	public String aggrFrameCode;
	
}
