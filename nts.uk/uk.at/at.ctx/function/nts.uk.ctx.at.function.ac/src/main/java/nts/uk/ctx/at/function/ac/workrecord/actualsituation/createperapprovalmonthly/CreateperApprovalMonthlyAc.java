package nts.uk.ctx.at.function.ac.workrecord.actualsituation.createperapprovalmonthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.AppDataInfoMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.CreateperApprovalMonthlyAdapter;
<<<<<<< HEAD
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.OutputCreatePerAppMonImport;
=======
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
>>>>>>> 2d85ea9... fixbug kbt002 108717
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly.CreateperApprovalMonthlyPub;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly.OutputCreatePerAppMonExport;

@Stateless
public class CreateperApprovalMonthlyAc implements CreateperApprovalMonthlyAdapter {

	@Inject
	private CreateperApprovalMonthlyPub createperApprovalMonthlyPub;

	@Override
	public OutputCreatePerAppMonImport createperApprovalMonthly(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, GeneralDate startDateClosure) {
<<<<<<< HEAD
		OutputCreatePerAppMonExport check = createperApprovalMonthlyPub.createperApprovalMonthly(companyId, executionId, employeeIDs, processExecType, startDateClosure);
		return new OutputCreatePerAppMonImport(check.isCreateperApprovalMon(), check.isCheckStop());
=======
		boolean check = createperApprovalMonthlyPub.createperApprovalMonthly(companyId, executionId, employeeIDs,
				processExecType, startDateClosure);
		return check;
>>>>>>> 2d85ea9... fixbug kbt002 108717
	}

	@Override
	public List<AppDataInfoMonthlyImport> getAppDataInfoMonthlyByExeID(String executionId) {
		List<AppDataInfoMonthly> data = createperApprovalMonthlyPub.getAppDataInfoMonthlyByExeID(executionId);
		return data.stream().map(c -> convertToDomain(c)).collect(Collectors.toList());
	}

	@Override
	public Optional<AppDataInfoMonthlyImport> getAppDataInfoMonthlyByID(String employeeId, String executionId) {
		Optional<AppDataInfoMonthly> data = createperApprovalMonthlyPub.getAppDataInfoMonthlyByID(employeeId,
				executionId);
		if (!data.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(convertToDomain(data.get()));
	}

	@Override
	public void addAppDataInfoMonthly(AppDataInfoMonthlyImport appDataInfoMonthly) {
		createperApprovalMonthlyPub.addAppDataInfoMonthly(convertToImport(appDataInfoMonthly));

	}

	private AppDataInfoMonthlyImport convertToDomain(AppDataInfoMonthly domain) {
		return new AppDataInfoMonthlyImport(domain.getEmployeeId(), domain.getExecutionId(),
				domain.getErrorMessage().v());
	}

	private AppDataInfoMonthly convertToImport(AppDataInfoMonthlyImport imp) {
		return new AppDataInfoMonthly(imp.getEmployeeId(), imp.getExecutionId(),
				new ErrorMessageRC(imp.getErrorMessage()));
	}

}
