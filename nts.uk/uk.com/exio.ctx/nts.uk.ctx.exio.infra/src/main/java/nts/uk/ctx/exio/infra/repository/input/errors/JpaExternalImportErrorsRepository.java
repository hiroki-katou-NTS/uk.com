package nts.uk.ctx.exio.infra.repository.input.errors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportErrors;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportErrorsRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportErrorsRepository extends JpaRepository implements ExternalImportErrorsRepository {

	@Override
	public void setup(ExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(ExecutionContext context, ExternalImportError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExternalImportErrors find(ExecutionContext context, int startErrorNo, int endErrorNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
