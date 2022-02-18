package nts.uk.ctx.exio.dom.input.canonicalize.result;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.context.ExecutionContext;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;

public interface CanonicalizedDataRecordRepository {

	void save(Require require, ExecutionContext context, CanonicalizedDataRecord record);
	
	List<String> getAllEmployeeIds(Require require, ExecutionContext context);
}
