package nts.uk.ctx.at.function.infra.repository.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogManage;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionLogManagePK;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class UpdateJDBCProcessExecutionLogManager extends JpaRepository {
	public void updateProcessExecutionLogManager(ProcessExecutionLogManage domain) {
		try {
			Optional<KfnmtProcessExecutionLogManage> entity = this.queryProxy().find(
					new KfnmtProcessExecutionLogManagePK(domain.getCompanyId(), domain.getExecItemCd().v()),
					KfnmtProcessExecutionLogManage.class);
			entity.ifPresent(e -> {
				e.currentStatus = domain.getCurrentStatus().map(d -> d.value).orElse(null);
				e.overallStatus = domain.getOverallStatus().map(d -> d.value).orElse(null);
				e.errorDetail = domain.getOverallError().map(d -> d.value).orElse(null);
				e.lastExecDateTime = domain.getLastExecDateTime().orElse(null);
				e.prevExecDateTimeEx = domain.getLastExecDateTimeEx().orElse(null);
				e.lastEndExecDateTime = domain.getLastEndExecDateTime().orElse(null);
				e.errorSystem = domain.getErrorSystem().map(d -> d ? 1 : 0).orElse(null);
				e.errorBusiness = domain.getErrorBusiness().map(d -> d ? 1 : 0).orElse(null);
				this.commandProxy().update(e);
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<ProcessExecutionLogManage> getLogByCIdAndExecCd(String companyId, String execItemCd) {
		return this.queryProxy()
				.find(new KfnmtProcessExecutionLogManagePK(companyId, execItemCd), KfnmtProcessExecutionLogManage.class)
				.map(KfnmtProcessExecutionLogManage::toDomain);
	}
}