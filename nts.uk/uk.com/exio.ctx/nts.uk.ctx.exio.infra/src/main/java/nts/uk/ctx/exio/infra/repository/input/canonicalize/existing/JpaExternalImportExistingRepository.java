package nts.uk.ctx.exio.infra.repository.input.canonicalize.existing;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.ExternalImportExistingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportExistingRepository extends JpaRepository implements ExternalImportExistingRepository {

	@Override
	public void setup(ExecutionContext context) {
		new LayoutAnyRecordToChange(jdbcProxy(), context).createTable();
		new LayoutAnyRecordToDelete(jdbcProxy(), context).createTable();
	}

	@Override
	public void save(ExecutionContext context, AnyRecordToChange record) {
		new LayoutAnyRecordToChange(jdbcProxy(), context).insert(record);
	}

	@Override
	public List<AnyRecordToChange> findAllChanges(ExecutionContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnyRecordToChange> findAllChangesWhere(ExecutionContext context, int keyItemNo, String keyValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ExecutionContext context, AnyRecordToDelete record) {
		new LayoutAnyRecordToDelete(jdbcProxy(), context).insert(record);
	}

	@Override
	public List<AnyRecordToDelete> findAllDeletes(ExecutionContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnyRecordToDelete> findAllDeletesWhere(ExecutionContext context, int keyItemNo, String keyValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
