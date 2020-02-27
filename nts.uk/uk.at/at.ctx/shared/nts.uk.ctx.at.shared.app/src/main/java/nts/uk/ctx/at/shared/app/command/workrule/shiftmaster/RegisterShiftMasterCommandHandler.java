package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.DefaultBasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.UpdateShiftMasterService;
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
	private MakeShiftMasterService makeShiftMasterService;
	
	@Inject
	private UpdateShiftMasterService updateShiftMasterService;
	
//	@Injectable
//	private Require requireWorkInfo;

	@Override
	protected void handle(CommandHandlerContext<RegisterShiftMasterCommand> context) {
		String companyId = AppContexts.user().companyId();
		RegisterShiftMasterCommand cmd = context.getCommand();
		Optional<ShiftMaster> existed = shiftMasterRepo.getByShiftMaterCd(companyId, new ShiftMasterCode(cmd.getShiftMasterCode()).v());
		if(cmd.getNewMode() && existed.isPresent()) {
			throw new BusinessException("Msg_1610");
		}
		if (existed.isPresent()) {
			shiftMasterRepo.update(cmd.toDomain());
		} else {
			shiftMasterRepo.insert(cmd.toDomain());
		}
		
		
	}
	
	@AllArgsConstructor
	private static class WorkInfoRequireImpl implements WorkInformation.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Inject
		private DefaultBasicScheduleService defaultBasicScheduleService;
		
		@Inject
		private WorkTypeRepository workTypeRepo;
		
		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return defaultBasicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}
	}
	
	@AllArgsConstructor
	private static class ShiftMasterRequireImpl implements WorkInformation.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Inject
		private DefaultBasicScheduleService defaultBasicScheduleService;
		
		@Inject
		private WorkTypeRepository workTypeRepo;
		
		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return defaultBasicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}
	}
}
