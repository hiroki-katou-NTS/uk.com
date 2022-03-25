package nts.uk.ctx.exio.dom.input.canonicalize.result;

import java.util.List;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;

public interface CanonicalizedDataRecordRepository {

	void save(Require require, ExecutionContext context, CanonicalizedDataRecord record);
	
	List<String> getAllEmployeeIds(Require require, ExecutionContext context);

	List<CanonicalizedDataRecord> findByCriteria(Require require, ExecutionContext context, ImportingDomainId domainId, int criteriaItemNo, String criteriaValue);
	
	List<CanonicalizedDataRecord> findByCriteria(Require require, ExecutionContext context, int criteriaItemNo, String criteriaValue);
}
