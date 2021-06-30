package nts.uk.ctx.exio.infra.repository.input.meta;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMetaRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaImportingDataMetaRepository extends JpaRepository implements ImportingDataMetaRepository {

	@Override
	public void setup(ExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ImportingDataMeta meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ImportingDataMeta> find(ExecutionContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
