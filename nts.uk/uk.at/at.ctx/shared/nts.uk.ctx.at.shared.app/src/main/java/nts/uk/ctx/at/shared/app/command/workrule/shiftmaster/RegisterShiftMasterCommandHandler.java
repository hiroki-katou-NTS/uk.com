package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.UpdateShiftMasterService;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Stateless
public class RegisterShiftMasterCommandHandler extends CommandHandler<RegisterShiftMasterCommand> {

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;



	@Override
	protected void handle(CommandHandlerContext<RegisterShiftMasterCommand> context) {
		String companyId = AppContexts.user().companyId();
		RegisterShiftMasterCommand cmd = context.getCommand();

		Optional<ShiftMaster> existed = shiftMasterRepo.getByShiftMaterCd(companyId,
				new ShiftMasterCode(cmd.getShiftMasterCode()).v());
		if (cmd.getNewMode() && existed.isPresent()) {
			throw new BusinessException("Msg_3");
		}

		WorkInformation.Require workRequired = new WorkInfoRequireImpl(basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService);
		ShiftMaster dom = cmd.toDomain();
		dom.checkError(workRequired);

		MakeShiftMasterService.Require createRequired = new MakeShiftMasterRequireImpl(shiftMasterRepo);
		UpdateShiftMasterService.Require updateRequired = new UpdateShiftMasterRequireImpl(shiftMasterRepo);
		AtomTask persist;
		if (cmd.getNewMode()) {
			persist = MakeShiftMasterService.makeShiftMater(workRequired, createRequired, companyId,
					cmd.getShiftMasterCode(), cmd.getWorkTypeCd(), Optional.ofNullable(cmd.getWorkTimeSetCd()),
					dom.getDisplayInfor());
		} else {
			persist = UpdateShiftMasterService.updateShiftMater(workRequired, updateRequired, cmd.getShiftMasterCode(),
					dom.getDisplayInfor(), new WorkInformation(cmd.getWorkTypeCd(), cmd.getWorkTimeSetCd()));
		}

		transaction.execute(() -> {
			persist.run();
		});

	}

	@AllArgsConstructor
	private static class WorkInfoRequireImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private BasicScheduleService service;

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private WorkTimeSettingService workTimeSettingService;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
	}

	@AllArgsConstructor
	private static class MakeShiftMasterRequireImpl implements MakeShiftMasterService.Require {

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public boolean checkExists(String companyId, String workTypeCd, String workTimeCd) {
			return shiftMasterRepo.checkExists(companyId, workTypeCd, workTimeCd);
		}

		@Override
		public boolean checkExistsByCode(String companyId, String shiftMaterCode) {
			return shiftMasterRepo.checkExistsByCd(companyId, shiftMaterCode);
		}


		@Override
		public void insert(ShiftMaster shiftMater, String workTypeCd, String workTimeCd) {
			shiftMasterRepo.insert(shiftMater);
		}

	}

	@AllArgsConstructor
	private static class UpdateShiftMasterRequireImpl implements UpdateShiftMasterService.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public void update(ShiftMaster shiftMater) {
			shiftMasterRepo.update(shiftMater);
		}

		@Override
		public Optional<ShiftMaster> getByShiftMaterCd(String shiftMaterCode) {
			return shiftMasterRepo.getByShiftMaterCd(companyId, shiftMaterCode);
		}

		@Override
		public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String workTypeCd, String workTimeCd) {
			return shiftMasterRepo.getByWorkTypeAndWorkTime(companyId, workTypeCd, workTimeCd);
		}

	}
}
