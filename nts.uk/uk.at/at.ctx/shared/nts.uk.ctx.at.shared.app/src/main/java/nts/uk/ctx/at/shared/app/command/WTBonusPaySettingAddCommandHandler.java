package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

@Stateless
public class WTBonusPaySettingAddCommandHandler extends CommandHandler<List<WTBonusPaySettingAddCommand>> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WTBonusPaySettingAddCommand>> context) {
		List<WTBonusPaySettingAddCommand> lstWTBonusPaySettingAddCommand = context.getCommand();
		this.wtBonusPaySettingRepository.addListSetting(lstWTBonusPaySettingAddCommand.stream()
				.map(c -> toWorkingTimesheetBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingAddCommand wtBonusPaySettingAddCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(wtBonusPaySettingAddCommand.getCompanyId().toString(),
				wtBonusPaySettingAddCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingAddCommand.getBonusPaySettingCode().toString());

	}

}
