package nts.uk.ctx.exio.infra.repository.input.canonicalize;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;
import nts.uk.ctx.exio.infra.repository.input.workspace.WorkspaceSql;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCanonicalizedDataRecordRepository extends JpaRepository implements CanonicalizedDataRecordRepository {

	@Override
	public void save(Require require, ExecutionContext context, CanonicalizedDataRecord record) {
		
		WorkspaceSql.create(require, context, jdbcProxy(), this.database().product()).insert(record);
	}

	@Override
	public List<String> getAllEmployeeIds(Require require, ExecutionContext context) {
		return WorkspaceSql.create(require, context, jdbcProxy(), this.database().product())
				.getAllEmployeeIdsOfCanonicalizedData();
	}

	@Override
	public Optional<String> getEmployeeBasicSID(Require require, ExecutionContext context, String employeeCode) {
		return WorkspaceSql.create(require, context, jdbcProxy(), this.database().product())
				.getEmployeeBasicSID(employeeCode);
	}

	@Override
	public Optional<String> getEmployeeBasicPID(Require require, ExecutionContext context, String sid) {
		return WorkspaceSql.create(require, context, jdbcProxy(), this.database().product())
				.getEmployeeBasicPID(sid);
	}
}
