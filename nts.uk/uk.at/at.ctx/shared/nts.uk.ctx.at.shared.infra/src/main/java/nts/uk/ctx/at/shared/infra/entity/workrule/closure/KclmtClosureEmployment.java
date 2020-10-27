package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KCLMT_CLOSURE_EMPLOYMENT")
public class KclmtClosureEmployment extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KclmpClosureEmploymentPK kclmpClosureEmploymentPK;
	
	@Column(name="CLOSURE_ID")
	public Integer closureId;

	@Override
	protected Object getKey() {
		return kclmpClosureEmploymentPK;
	}
}
