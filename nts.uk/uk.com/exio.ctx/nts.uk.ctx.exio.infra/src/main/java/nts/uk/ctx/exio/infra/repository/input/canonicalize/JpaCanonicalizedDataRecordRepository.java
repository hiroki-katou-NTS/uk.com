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
	public List<CanonicalizedDataRecord> findByCriteria(Require require, ExecutionContext context, ImportingDomainId domainId, int criteriaItemNo, String criteriaValue){
		//実行コンテキストを偽装
		val impersonatedContext = new ExecutionContext(context.getCompanyId(), 
																						   context.getSettingCode(),
																						   domainId,
																						   context.getMode());	
		
		return WorkspaceSql.create(require, impersonatedContext, jdbcProxy(), this.database().product())
				.findCanonicalizedWhere(criteriaItemNo, criteriaValue);
				
	}
}
