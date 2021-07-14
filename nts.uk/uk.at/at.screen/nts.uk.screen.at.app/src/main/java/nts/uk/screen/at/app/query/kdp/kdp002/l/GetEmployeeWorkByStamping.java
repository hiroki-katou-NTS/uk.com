package nts.uk.screen.at.app.query.kdp.kdp002.l;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.GetWorkAvailableToEmployeesService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee.TaskAssignEmployeeRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力で社員利用可能作業を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).L:打刻作業選択.メニュー別OCD.打刻入力で社員利用可能作業を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetEmployeeWorkByStamping {

	@Inject
	private TaskFrameUsageSettingRepository taskFrameRepo;

	@Inject
	private GetWorkAvailableToEmployeesService getWork;

	@Inject
	private EmployeeRequestAdapter requestAdapter;

	@Inject
	private NarrowingByWorkplaceRepository narrowingByWorkplaceRepo;

	@Inject
	private TaskingRepository taskRepo;

	@Inject
	private TaskFrameUsageSettingRepository taskFrameUsageSettingRepo;

	@Inject
	private TaskAssignEmployeeRepository taskAssEmployee;

	public GetEmployeeWorkByStampingDto getEmployeeWorkByStamping(GetEmployeeWorkByStampingInput param) {

		GetEmployeeWorkByStampingDto result = new GetEmployeeWorkByStampingDto();

		String cid = AppContexts.user().companyId();
		GeneralDate today = GeneralDate.today();

		Optional<TaskCode> taskCode = Optional.empty();

		if (param.getUpperFrameWorkCode() != null) {
			taskCode = Optional.of(new TaskCode(param.getUpperFrameWorkCode()));
		}

		GetWorkAvailableToEmployeesServiceImp require = new GetWorkAvailableToEmployeesServiceImp();

		TaskFrameUsageSetting taskFrame = taskFrameRepo.getWorkFrameUsageSetting(cid);

		if (taskFrame != null) {
			Optional<TaskFrameSetting> frameInfo = taskFrame.getFrameSettingList().stream()
					.filter(f -> f.getTaskFrameNo().v() == param.getWorkFrameNo()).findFirst();

			result.setTaskFrameUsageSetting(
					new TaskFrameUsageSettingDto(frameInfo.map(m -> m.getTaskFrameNo().v()).orElse(null),
							frameInfo.map(m -> m.getTaskFrameName().v()).orElse(null),
							frameInfo.map(m -> m.getUseAtr().value == 1 ? true : false).orElse(false)));
		}

		List<Task> tasks = getWork.get(require, cid, param.getSid(), today, new TaskFrameNo(param.getWorkFrameNo()),
				taskCode);

		result.setTask(tasks.stream().map(m -> {
			return new TaskDto(m.getCode().v(), m.getTaskFrameNo().v(),
					new ExternalCooperationInfoDto(m.getCooperationInfo().getExternalCode1().map(a -> a.v()).orElse(""),
							m.getCooperationInfo().getExternalCode2().map(a -> a.v()).orElse(""),
							m.getCooperationInfo().getExternalCode3().map(a -> a.v()).orElse(""),
							m.getCooperationInfo().getExternalCode4().map(a -> a.v()).orElse(""),
							m.getCooperationInfo().getExternalCode5().map(a -> a.v()).orElse("")),
					m.getChildTaskList().stream().map(b -> b.v()).collect(Collectors.toList()),
					m.getExpirationDate().start(), m.getExpirationDate().end(),
					new TaskDisplayInfoDto(m.getDisplayInfo().getTaskName().v(), m.getDisplayInfo().getTaskAbName().v(),
							m.getDisplayInfo().getColor().map(c -> c.v()).orElse(""),
							m.getDisplayInfo().getTaskNote().map(d -> d.v()).orElse("")));
		}).collect(Collectors.toList()));

		return result;
	}

	private class GetWorkAvailableToEmployeesServiceImp implements GetWorkAvailableToEmployeesService.Require {

		@Override
		public List<String> findWpkIdsBySid(String companyId, String sid, GeneralDate date) {
			return requestAdapter.findWpkIdsBySid(companyId, sid, date);
		}

		@Override
		public Optional<NarrowingDownTaskByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo) {
			return narrowingByWorkplaceRepo.getOptionalWork(workPlaceId, taskFrameNo);
		}

		@Override
		public TaskFrameUsageSetting getWorkFrameUsageSetting(String cid) {
			return taskFrameUsageSettingRepo.getWorkFrameUsageSetting(cid);
		}

		@Override
		public List<Task> getListTask(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos) {
			return taskRepo.getListTask(cid, referenceDate, taskFrameNos);
		}

		@Override
		public List<Task> getListTask(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo,
				List<TaskCode> codes) {
			return taskRepo.getListTask(cid, referenceDate, taskFrameNo, codes);
		}

		@Override
		public Optional<Task> getOptionalTask(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
			return taskRepo.getOptionalTask(cid, taskFrameNo, code);
		}

		@Override
		public List<TaskAssignEmployee> get(String employeeId, int taskFrameNo) {
			return taskAssEmployee.get(employeeId, taskFrameNo);
		}

	}
}
