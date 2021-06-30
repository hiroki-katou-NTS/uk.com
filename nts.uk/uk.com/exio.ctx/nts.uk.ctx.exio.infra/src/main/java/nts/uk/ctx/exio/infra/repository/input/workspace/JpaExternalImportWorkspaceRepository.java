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
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaExternalImportWorkspaceRepository extends JpaRepository implements ExternalImportWorkspaceRepository {
	
	@Override
	public void createWorkspace(Require require, ExecutionContext context) {
		
		val workspace = createWorkspaceSql(require, context);
		
		// 編集済み一時テーブル
		workspace.createTableRevised(require);
		
		// 正準化済み一時テーブル
		workspace.createTableCanonicalized(require);
	}

	@Override
	public void save(Require require, ExecutionContext context, RevisedDataRecord record) {
		
		val builder = createWorkspaceSql(require, context);
		builder.insert(require, record);
	}

	@Override
	public void save(Require require, ExecutionContext context, CanonicalizedDataRecord record) {

		val builder = createWorkspaceSql(require, context);
		builder.insert(require, record);
	}

	private WorkspaceSql createWorkspaceSql(Require require, ExecutionContext context) {
		
		val group = require.getImportingGroup(context.getGroupId());
		val workspace = require.getGroupWorkspace(context.getGroupId());
		
		return new WorkspaceSql(context, group, workspace, jdbcProxy());
	}

	@Override
	public int getMaxRowNumberOfRevisedData(ExecutionContext context) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getStringsOfRevisedData(ExecutionContext context, int itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<RevisedDataRecord> findRevisedByRowNo(ExecutionContext context, int rowNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RevisedDataRecord> findRevisedWhere(ExecutionContext context, int itemNoCondition,
			String conditionString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllEmployeeIdsOfCanonicalizedData(ExecutionContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
