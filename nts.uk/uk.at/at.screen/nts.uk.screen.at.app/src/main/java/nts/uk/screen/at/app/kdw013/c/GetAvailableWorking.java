package nts.uk.screen.at.app.kdw013.c;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.GetWorkAvailableToEmployeesService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee.TaskAssignEmployeeRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.C:作業入力パネル.メニュー別OCD.利用可能作業を取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetAvailableWorking {

	@Inject
	private TaskFrameUsageSettingRepository taskFrameUsageSettingRepository;

	@Inject
	private TaskingRepository taskingRepo;

	@Inject
	private TaskAssignEmployeeRepository taskAssignEmployeeRepo;

	@Inject
	private EmployeeAdapter employeeAdapter;

	@Inject
	private NarrowingByWorkplaceRepository narrowRepo;

	/**
	 * 
	 * @param sId         社員ID
	 * @param refDate     基準日
	 * @param taskFrameNo 作業枠NO
	 * @param taskCode   上位枠作業コード
	 * @return
	 */
	public List<Task> get(String sId, GeneralDate refDate, TaskFrameNo taskFrameNo, Optional<TaskCode> taskCode) {
		RequireImpl require = new RequireImpl(taskingRepo, taskAssignEmployeeRepo, taskFrameUsageSettingRepository,
				employeeAdapter, narrowRepo);

		return GetWorkAvailableToEmployeesService.get(require, AppContexts.user().companyId(), sId, refDate,
				taskFrameNo, taskCode);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetWorkAvailableToEmployeesService.Require {

		private TaskingRepository taskingRepo;

		private TaskAssignEmployeeRepository taskAssignEmployeeRepo;

		private TaskFrameUsageSettingRepository taskFrameUsageSettingRepository;

		private EmployeeAdapter employeeAdapter;

		private NarrowingByWorkplaceRepository narrowRepo;

		@Override
		public List<String> findWpkIdsBySid(String companyId, String sid, GeneralDate date) {
			return employeeAdapter.findWpkIdsBySid(companyId, sid, date);
		}

		@Override
		public Optional<NarrowingDownTaskByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo) {
			return narrowRepo.getOptionalWork(workPlaceId, taskFrameNo);
		}

		@Override
		public TaskFrameUsageSetting getWorkFrameUsageSetting(String cid) {
			return taskFrameUsageSettingRepository.getWorkFrameUsageSetting(cid);
		}

		@Override
		public List<Task> getListTask(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos) {
			return taskingRepo.getListTask(cid, referenceDate, taskFrameNos);
		}

		@Override
		public List<Task> getListTask(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo,
				List<TaskCode> codes) {
			return taskingRepo.getListTask(cid, referenceDate, taskFrameNo, codes);
		}

		@Override
		public Optional<Task> getOptionalTask(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
			return taskingRepo.getOptionalTask(cid, taskFrameNo, code);
		}

		@Override
		public List<TaskAssignEmployee> get(String employeeId, int taskFrameNo) {
			return taskAssignEmployeeRepo.get(employeeId, taskFrameNo);
		}
	}
}
