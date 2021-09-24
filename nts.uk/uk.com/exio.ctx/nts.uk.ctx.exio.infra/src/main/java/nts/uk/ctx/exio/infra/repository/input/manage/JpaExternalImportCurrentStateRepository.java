package nts.uk.ctx.exio.infra.repository.input.manage;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentState;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentStateRepository;
import nts.uk.ctx.exio.infra.entity.input.manage.XimdtCurrentState;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportCurrentStateRepository extends JpaRepository implements ExternalImportCurrentStateRepository {

	@Override
	public ExternalImportCurrentState find(String companyId) {
		
		return this.queryProxy().find(companyId, XimdtCurrentState.class)
				.map(e -> e.toDomain())
				.orElseGet(() -> ExternalImportCurrentState.createNew(companyId));
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(ExternalImportCurrentState currentState) {
		
		val entity = XimdtCurrentState.toEntity(currentState);
		
		val existing = this.queryProxy().find(currentState.getCompanyId(), XimdtCurrentState.class);
		if (existing.isPresent()) {
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(entity);
		}
	}

}
