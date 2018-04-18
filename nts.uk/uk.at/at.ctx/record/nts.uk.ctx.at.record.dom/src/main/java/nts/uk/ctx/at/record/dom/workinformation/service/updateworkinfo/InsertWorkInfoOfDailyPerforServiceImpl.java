package nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.domainservice.CreateApprovalStaOfDailyPerforService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;

@Stateless
public class InsertWorkInfoOfDailyPerforServiceImpl implements InsertWorkInfoOfDailyPerforService {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private UpdateWorkInfoOfDailyPerforService updateWorkInfoOfDailyPerforService;
	
	@Inject
	private CreateApprovalStaOfDailyPerforService createApprovalStaOfDailyPerforService;

	@Override
	public void updateWorkInfoOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformanceUpdate) {

		Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance = this.workInformationRepository
				.find(employeeID, processingDate);

		if (workInfoOfDailyPerformance.isPresent()) {
			// 日別実績の勤務情報を更新する - update
			this.updateWorkInfoOfDailyPerforService.updateWorkInfoOfDailyPerforService(companyId, employeeID,
					processingDate, workInfoOfDailyPerformanceUpdate);

		} else {
			// ドメインモデル「日別実績の勤務情報」を登録 - insert
			this.workInformationRepository.insert(workInfoOfDailyPerformanceUpdate);
//			this.createApprovalStaOfDailyPerforService.createApprovalStaOfDailyPerforService(companyId, employeeID, processingDate);
		}

	}

}
