package nts.uk.ctx.exio.dom.input.workspace;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;

/**
 * 外部受入のワークスペースに対する入出力を担当するRepository
 */
public interface ExternalImportWorkspaceRepository {
	
	void setup(Require require, ExecutionContext context);

	void save(Require require, ExecutionContext context, RevisedDataRecord record);
	
	int getMaxRowNumberOfRevisedData(Require require, ExecutionContext context);
	
	List<String> getStringsOfRevisedData(Require require, ExecutionContext context, int itemNo);
	
	Optional<RevisedDataRecord> findRevisedByRowNo(Require require, ExecutionContext context, int rowNo);
	
	List<RevisedDataRecord> findRevisedWhere(
			Require require, ExecutionContext context, int itemNoCondition, String conditionString);
	
	void save(Require require, ExecutionContext context, CanonicalizedDataRecord record);
	
	List<String> getAllEmployeeIdsOfCanonicalizedData(Require require, ExecutionContext context);
	
	public static interface Require extends WorkspaceItem.RequireConfigureDataType {
		
		ImportingGroup getImportingGroup(ImportingGroupId groupId);
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
