package nts.uk.ctx.at.shared.app.command.vacation.setting.specialleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TimeSpecialLeaveSaveCommandHandler.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TimeSpecialLeaveSaveCommandHandler extends CommandHandler<TimeSpecialLeaveSaveCommand> {

	@Inject
	private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
	
	// ドメインモデル「時間特別休暇の管理設定」を取得
	@Override
	protected void handle(CommandHandlerContext<TimeSpecialLeaveSaveCommand> context) {
		String companyId = AppContexts.user().companyId();
		TimeSpecialLeaveSaveCommand command = context.getCommand();
		Optional<TimeSpecialLeaveManagementSetting> domain = this.timeSpecialLeaveMngSetRepository
				.findByCompany(companyId);

		TimeSpecialLeaveManagementSetting setting = command.toDomain(companyId);
		if (domain.isPresent()) {
			this.timeSpecialLeaveMngSetRepository.update(setting);
		} else {
			this.timeSpecialLeaveMngSetRepository.add(setting);
		}
	}
}
