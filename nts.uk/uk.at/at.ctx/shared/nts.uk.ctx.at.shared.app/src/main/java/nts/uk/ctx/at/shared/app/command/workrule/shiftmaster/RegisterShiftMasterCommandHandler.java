package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Stateless
public class RegisterShiftMasterCommandHandler extends CommandHandler<RegisterShiftMasterCommand> {

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterShiftMasterCommand> context) {
		String companyId = AppContexts.user().companyId();
		RegisterShiftMasterCommand cmd = context.getCommand();
		Optional<ShiftMaster> existed = shiftMasterRepo.getByShiftMaterCd(companyId, new ShiftMasterCode(cmd.getShiftMasterCode()).v());
		if (existed.isPresent()) {
			shiftMasterRepo.update(cmd.toDomain());
		} else {
			shiftMasterRepo.insert(cmd.toDomain());

		}
	}
}
