package nts.uk.ctx.at.record.pubimp.dailymonthlyprocessing;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.ComplStateOfExeContentsExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.EmployeeExecutionStatusExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.ExecutionContentExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.TargetPersonExport;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.TargetPersonPub;

@Stateless
public class TargetPersonPubImpl implements TargetPersonPub{
	@Inject
	private TargetPersonRepository targetRepos;
	@Override
	public List<TargetPersonExport> getTargetPerson(String empCalAndSumExecLogId) {
		return targetRepos.getByempCalAndSumExecLogID(empCalAndSumExecLogId)
				.stream()
				.map(x -> {
					List<ComplStateOfExeContentsExport> state = x.getState().stream()
							.map(a -> new ComplStateOfExeContentsExport(EnumAdaptor.valueOf(a.getExecutionContent().value, ExecutionContentExport.class)
									, EnumAdaptor.valueOf(a.getStatus().value, EmployeeExecutionStatusExport.class)))
							.collect(Collectors.toList());
					return new TargetPersonExport(x.getEmployeeId(), x.getEmpCalAndSumExecLogId(), state);
				}).collect(Collectors.toList());
		
	}

}
