package nts.uk.ctx.exio.infra.repository.input.workspace;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportWorkspaceRepository extends JpaRepository implements ExternalImportWorkspaceRepository {
	
	@Override
	public void createWorkspace(Require require, ExecutionContext context) {
		
		val builder = createSqlBuilder(require, context);
		
		// 編集済み一時テーブル
		this.jdbcProxy().query(builder.createTableRevised(require)).execute();
		
		// 正準化済み一時テーブル
		this.jdbcProxy().query(builder.createTableCanonicalized(require)).execute();
	}

	@Override
	public void save(Require require, ExecutionContext context, RevisedDataRecord record) {
		
		val builder = createSqlBuilder(require, context);
		builder.executeInsert(require, record, jdbcProxy());
	}

	@Override
	public void save(Require require, ExecutionContext context, CanonicalizedDataRecord record) {

		val builder = createSqlBuilder(require, context);
		builder.executeInsert(require, record, jdbcProxy());
	}

	@Override
	public void save(ExecutionContext context, AnyRecordToChange record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ExecutionContext context, AnyRecordToDelete record) {
		// TODO Auto-generated method stub
		
	}

	private WorkspaceSql createSqlBuilder(Require require, ExecutionContext context) {
		
		val group = require.getImportingGroup(context.getGroupId());
		val workspace = require.getGroupWorkspace(context.getGroupId());
		
		return new WorkspaceSql(context, group, workspace);
	}
}
