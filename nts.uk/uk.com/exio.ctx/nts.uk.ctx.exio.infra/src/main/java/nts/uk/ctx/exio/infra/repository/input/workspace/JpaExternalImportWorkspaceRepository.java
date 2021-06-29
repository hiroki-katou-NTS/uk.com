package nts.uk.ctx.exio.infra.repository.input.workspace;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportWorkspaceRepository extends JpaRepository implements ExternalImportWorkspaceRepository {
	
	@Override
	public void createWorkspaceReviced(Require require, ExecutionContext context) {
		
		val builder = createSqlBuilder(require, context);
		String sql = builder.createTableRevised(require);
		
		this.jdbcProxy().query(sql).execute();
	}

	@Override
	public void save(Require require, ExecutionContext context, RevisedDataRecord record) {
		
		val builder = createSqlBuilder(require, context);
		builder.executeInsert(require, record, jdbcProxy());
	}

	@Override
	public void createWorkspaceCanonicalized(Require require, ExecutionContext context) {

		val builder = createSqlBuilder(require, context);
		String sql = builder.createTableCanonicalized(require);
		
		this.jdbcProxy().query(sql).execute();
	}

	@Override
	public void save(Require require, ExecutionContext context, CanonicalizedDataRecord record) {

		val builder = createSqlBuilder(require, context);
		builder.executeInsert(require, record, jdbcProxy());
	}

	private WorkspaceSql createSqlBuilder(Require require, ExecutionContext context) {
		
		val group = require.getImportingGroup(context.getGroupId());
		val workspace = require.getGroupWorkspace(context.getGroupId());
		
		return new WorkspaceSql(context, group, workspace);
	}
}
