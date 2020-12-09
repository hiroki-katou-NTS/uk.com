package nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_OPTIONALAGGR_PERIOD")
public class KrcmtOptionalAggrPeriod extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtOptionalAggrPeriodPK krcmtOptionalAggrPeriodPK;

	@Column(name = "OPTIONAL_AGGR_NAME")
	public String optionalAggrName;
	
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krcmtOptionalAggrPeriodPK;
	}


}
