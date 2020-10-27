package nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_AGGR_PERIOD_TARGET")
public class KrcmtAggrPeriodTarget extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtAggrPeriodTargetPK krcmtAggrPeriodTargetPK;
	
	@Column(name = "STATE")
	public int state;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krcmtAggrPeriodTargetPK;
	}

}
