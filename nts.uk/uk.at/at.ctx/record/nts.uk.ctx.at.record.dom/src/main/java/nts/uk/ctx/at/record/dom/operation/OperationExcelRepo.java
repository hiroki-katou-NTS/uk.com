package nts.uk.ctx.at.record.dom.operation;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;

public interface OperationExcelRepo {
	Optional<DaiPerformanceFun> getDaiPerformanceFunById(String cid);
	Optional<MonPerformanceFun> getMonPerformanceFunById(String cid);
	Optional<FormatPerformance> getFormatPerformanceById(String cid);
	Optional<IdentityProcess> getIdentityProcessById(String cid);
	Optional<ApprovalProcess> getApprovalProcessById(String cid);
	List<ApplicationCallExport> findByCom(String companyId);
	List<RoleExport> findRole(String companyId);
}
