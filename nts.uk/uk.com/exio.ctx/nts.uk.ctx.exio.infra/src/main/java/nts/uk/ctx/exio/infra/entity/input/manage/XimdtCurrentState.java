package nts.uk.ctx.exio.infra.entity.input.manage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.input.manage.ExecutionState;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentState;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "XIMDT_CURRENT_STATE")
@AllArgsConstructor
@NoArgsConstructor
public class XimdtCurrentState extends ContractUkJpaEntity {
	
	@Id
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "EXECUTION_STATE")
	public int executionState;
	
	@Override
	protected Object getKey() {
		return companyId;
	}
	
	public static XimdtCurrentState toEntity(ExternalImportCurrentState domain) {
		return new XimdtCurrentState(domain.getCompanyId(), domain.getExecutionState().value);
	}
	
	public ExternalImportCurrentState toDomain() {
		return new ExternalImportCurrentState(companyId, ExecutionState.valueOf(executionState));
	}
}
