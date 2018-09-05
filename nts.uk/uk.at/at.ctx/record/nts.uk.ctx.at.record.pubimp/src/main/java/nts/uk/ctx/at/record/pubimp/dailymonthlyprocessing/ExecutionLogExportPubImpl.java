package nts.uk.ctx.at.record.pubimp.dailymonthlyprocessing;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.SetInforReflAprResult;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.ExecutionContentExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.ExecutionLogExportPub;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.ExecutionTypeExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.SetInforReflAprResultExport;

@Stateless
public class ExecutionLogExportPubImpl implements ExecutionLogExportPub{
	@Inject
	private ExecutionLogRepository logRepo;
	@Override
	public Optional<SetInforReflAprResultExport> optReflectResult(String empCalAndSumExecLogID, int executionContent) {
		Optional<ExecutionLog> optExeLog = logRepo.getByExecutionContent(empCalAndSumExecLogID, executionContent);
		if(!optExeLog.isPresent()) {
			return Optional.empty();
		}
		ExecutionLog exeLog = optExeLog.get();
		Optional<SetInforReflAprResult> reflectApprovalSetInfo = exeLog.getReflectApprovalSetInfo();
		if(!reflectApprovalSetInfo.isPresent()) {
			return Optional.empty();
		}
		SetInforReflAprResult refInfo = reflectApprovalSetInfo.get();
		SetInforReflAprResultExport output = new SetInforReflAprResultExport(EnumAdaptor.valueOf(refInfo.getExecutionContent().value, ExecutionContentExport.class),
				EnumAdaptor.valueOf(refInfo.getExecutionType().value, ExecutionTypeExport.class),
				refInfo.getCalExecutionSetInfoID(),
				refInfo.isForciblyReflect());
		return Optional.of(output);
	}

}
