package nts.uk.ctx.at.shared.infra.entity.correctionofdailyperformance.errorreference;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "KRCDT_EMP_DP_ER_LIST")
public class KrcdtEDPErrorList extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrcdtEDPErrorListPK krcdtEDPErrorListPK;
	
	@Column(name = "TIME_ITEM_ID")
	public BigDecimal timeItemId;
	
	@Column(name = "RELEASE_ERROR")
	public BigDecimal releaseError;

	@Override
	protected Object getKey() {
		return this.krcdtEDPErrorListPK;
	}

}
