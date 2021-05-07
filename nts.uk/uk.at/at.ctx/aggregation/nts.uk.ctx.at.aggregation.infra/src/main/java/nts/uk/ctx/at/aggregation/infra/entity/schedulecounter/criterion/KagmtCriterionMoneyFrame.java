package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

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
@Table(name = "KAGMT_CRITERION_MONEY_FRAME")
public class KagmtCriterionMoneyFrame extends ContractUkJpaEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KagmtCriterionMoneyFramePk pk;
	
	
	@Column(name = "COLOR", nullable = false)
    public String color;

	@Override
	protected Object getKey() {
		
		return this.pk;
	}

}
