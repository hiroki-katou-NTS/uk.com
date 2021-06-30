package nts.uk.ctx.exio.dom.input.workspace;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public interface ExternalImportWorkspaceRepository {
	
	void createWorkspace(Require require, ExecutionContext context);

	void save(Require require, ExecutionContext context, RevisedDataRecord record);
	
	int getMaxRowNumberOfRevisedData(ExecutionContext context);
	
	List<String> getStringsOfRevisedData(ExecutionContext context, int itemNo);
	
	Optional<RevisedDataRecord> findRevisedByRowNo(ExecutionContext context, int rowNo);
	
	List<RevisedDataRecord> findRevisedWhere(
			ExecutionContext context, int itemNoCondition, String conditionString);
	
	void save(ExecutionContext context, AnyRecordToChange record);
	
	void save(ExecutionContext context, AnyRecordToDelete record);
	
	void save(Require require, ExecutionContext context, CanonicalizedDataRecord record);
	
	public static interface Require extends WorkspaceItem.RequireConfigureDataType {
		
		ImportingGroup getImportingGroup(ImportingGroupId groupId);
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
