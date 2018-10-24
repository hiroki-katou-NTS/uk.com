package nts.uk.ctx.at.request.ac.record;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.dailymonthlyprocessing.TargetPersonPub;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ComplStateOfExeContentsImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.EmployeeExecutionStatusImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionContentImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.TargetPersonRequestImport;

@Stateless
public class TargetPersonRequestImportImpl implements TargetPersonRequestImport {
	@Inject
	private TargetPersonPub targetPub;
	@Override
	public List<TargetPersonImport> getTargetPerson(String empCalAndSumExecLogId) {
		return targetPub.getTargetPerson(empCalAndSumExecLogId)
				.stream()
				.map(x -> {
					List<ComplStateOfExeContentsImport> state = x.getState().stream()
							.map(y -> new ComplStateOfExeContentsImport(EnumAdaptor.valueOf(y.getExecutionContent().value, ExecutionContentImport.class),
									EnumAdaptor.valueOf(y.getStatus().value, EmployeeExecutionStatusImport.class)))
							.collect(Collectors.toList());
					return new TargetPersonImport(x.getEmployeeId(), x.getEmpCalAndSumExecLogId(), state);
				}).collect(Collectors.toList());
	}

}
