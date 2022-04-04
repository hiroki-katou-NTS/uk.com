/**
 * 
 */
package nts.uk.ctx.at.shared.app.find.scherec.taskmanagement.taskmaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.app.query.task.GetAvailableTaskMasterQuery;
import nts.uk.ctx.at.shared.app.query.task.TaskDto;
import nts.uk.ctx.at.shared.app.query.task.TaskMasterDto;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
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
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * 	UKDesign.UniversalK.就業.KDL_ダイアログ.KDLS12_作業選択ダイアログ（スマホ）.メニュー別OCD.作業選択ダイアログの初期起動
 *	UKDesign.UniversalK.就業.KDL_ダイアログ.KDL012_作業選択ダイアログ.メニュー別OCD.作業選択ダイアログの初期起動
 *
 */
@Stateless
public class TaskMasterFinder {
	
	@Inject
    private GetAvailableTaskMasterQuery getAvailableTaskMasterQuery;
    @Inject
    private GetWorkAvailableToEmployeesService getWorkAvailableToEmployeesService;
	@Inject
	private TaskFrameUsageSettingRepository taskFrameUsageSettingRepository;
	@Inject
	private TaskingRepository taskingRepo;
	@Inject
	private TaskAssignEmployeeRepository taskAssignEmployeeRepo;
	@Inject
	private SharedAffWorkPlaceHisAdapter workplaceAdapter;
	@Inject
	private NarrowingByWorkplaceRepository narrowRepo;
	
    @SuppressWarnings("static-access")
	public List<TaskMasterDto> getListTask(TaskDto request) {
        val taskFrameNo = Collections.singletonList(new TaskFrameNo(request.getTaskFrameNo()));
        List<Task> data = new ArrayList<Task>();
        //	社員IDが存在するか？
		if (!StringUtil.isNullOrEmpty(request.getSid(), true)) {
			RequireImpl require = new RequireImpl(taskingRepo, taskAssignEmployeeRepo, taskFrameUsageSettingRepository,
					workplaceAdapter, narrowRepo);
			//	1.1.取得する(@Require, 会社ID, 社員ID, 年月日, 作業枠NO, 作業コード)：List<作業>
			data = getWorkAvailableToEmployeesService.get(
					require, 
					AppContexts.user().companyId(), 
					request.getSid(), 
					GeneralDate.fromString(request.getBaseDate(), "yyyy/MM/dd"), 
					new TaskFrameNo(request.getTaskFrameNo()), 
					Optional.of(new TaskCode(request.getTaskCode())));
		} else {
			//	１．取得する(作業枠NO, 基準日)：List<作業>
			data = getAvailableTaskMasterQuery.getListTask(request.getBaseDate(), taskFrameNo);
		}

        return data.stream().map(e ->
                new TaskMasterDto(
                        e.getCode().v(),
                        e.getDisplayInfo().getTaskName().v(),
                        e.getDisplayInfo().getTaskAbName().v(),
                        e.getExpirationDate().start(),
                        e.getExpirationDate().end(),
                        e.getDisplayInfo().getTaskNote().isPresent() ? e.getDisplayInfo().getTaskNote()
                                .get().v() : null
                )
        ).collect(Collectors.toList());
    }
    
	@AllArgsConstructor
	private static class RequireImpl implements GetWorkAvailableToEmployeesService.Require {

		@Inject
		private TaskingRepository taskingRepo;
		@Inject
		private TaskAssignEmployeeRepository taskAssignEmployeeRepo;
		@Inject
		private TaskFrameUsageSettingRepository taskFrameUsageSettingRepository;
		@Inject
		private SharedAffWorkPlaceHisAdapter workplaceAdapter;
		@Inject
		private NarrowingByWorkplaceRepository narrowRepo;

		@Override
		public List<String> findWpkIdsBySid(String employeeID, GeneralDate date) {
			return workplaceAdapter.findWpkIdsBySid(AppContexts.user().companyId(), employeeID, date);
		}

		@Override
		public Optional<NarrowingDownTaskByWorkplace> getNarrowingDownTaskByWorkplace(String workPlaceId,
				TaskFrameNo taskFrameNo) {
			return narrowRepo.getOptionalWork(workPlaceId, taskFrameNo);
		}

		@Override
		public TaskFrameUsageSetting getTask() {
			return taskFrameUsageSettingRepository.getWorkFrameUsageSetting(AppContexts.user().companyId());
		}

		@Override
		public List<Task> getTask(GeneralDate date, List<TaskFrameNo> taskFrameNo) {
			return taskingRepo.getListTask(AppContexts.user().companyId(), date, taskFrameNo);
		}

		@Override
		public List<Task> getListTask(GeneralDate referenceDate, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
			return taskingRepo.getListTask(AppContexts.user().companyId(), referenceDate, taskFrameNo, codes);
		}

		@Override
		public Optional<Task> getOptionalTask(TaskFrameNo taskFrameNo, TaskCode code) {
			return taskingRepo.getOptionalTask(AppContexts.user().companyId(), taskFrameNo, code);
		}

		@Override
		public List<TaskAssignEmployee> getTaskAssignEmployee(String employeeId, TaskFrameNo taskFrameNo) {
			return taskAssignEmployeeRepo.get(employeeId, taskFrameNo.v());
		}
	}

}
