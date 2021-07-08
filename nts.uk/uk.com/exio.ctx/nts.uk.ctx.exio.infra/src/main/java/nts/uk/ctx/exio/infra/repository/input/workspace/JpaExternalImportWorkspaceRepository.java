package nts.uk.ctx.exio.infra.repository.input.workspace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportWorkspaceRepository extends JpaRepository implements ExternalImportWorkspaceRepository {
	
	@Override
	public void setup(Require require, ExecutionContext context) {
		
		val workspace = createWorkspaceSql(require, context);
		
		// 編集済み一時テーブル
		workspace.createTableRevised(require);
		
		// 正準化済み一時テーブル
		workspace.createTableCanonicalized(require);
	}

	@Override
	public void save(Require require, ExecutionContext context, RevisedDataRecord record) {
		createWorkspaceSql(require, context).insert(require, record);
	}

	@Override
	public void save(Require require, ExecutionContext context, CanonicalizedDataRecord record) {
		createWorkspaceSql(require, context).insert(require, record);
	}

	@Override
	public int getMaxRowNumberOfRevisedData(Require require, ExecutionContext context) {
		return createWorkspaceSql(require, context).getMaxRowNumberOfRevisedData();
	}

	@Override
	public List<String> getStringsOfRevisedData(Require require, ExecutionContext context, int itemNo) {
		
		val workspace = require.getGroupWorkspace(context.getGroupId());
		String columnName = workspace.getItem(itemNo).get().getName();
		return createWorkspaceSql(require, context).getStringsOfRevisedData(columnName);
	}

	@Override
	public Optional<RevisedDataRecord> findRevisedByRowNo(Require require, ExecutionContext context, int rowNo) {
		return createWorkspaceSql(require, context).findRevisedByRowNo(require, rowNo);
	}

	@Override
	public List<RevisedDataRecord> findRevisedWhere(
			Require require, ExecutionContext context, int itemNoCondition, String conditionString) {
		return createWorkspaceSql(require, context).findRevisedWhere(require, itemNoCondition, conditionString);
	}

	@Override
	public List<String> getAllEmployeeIdsOfCanonicalizedData(Require require, ExecutionContext context) {
		return createWorkspaceSql(require, context).getAllEmployeeIdsOfCanonicalizedData();
	}

	private WorkspaceSql createWorkspaceSql(Require require, ExecutionContext context) {
		
		val group = require.getImportingGroup(context.getGroupId());
		val workspace = require.getGroupWorkspace(context.getGroupId());
		
		return new WorkspaceSql(context, group, workspace, jdbcProxy());
	}
}
