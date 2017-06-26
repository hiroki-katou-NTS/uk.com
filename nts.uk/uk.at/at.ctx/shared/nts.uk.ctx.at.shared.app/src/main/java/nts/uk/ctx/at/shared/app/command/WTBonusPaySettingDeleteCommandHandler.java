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
public class WTBonusPaySettingDeleteCommandHandler extends CommandHandler<List<WTBonusPaySettingDeleteCommand>> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WTBonusPaySettingDeleteCommand>> context) {
		List<WTBonusPaySettingDeleteCommand> lstWTBonusPaySettingDeleteCommand = context.getCommand();
		this.wtBonusPaySettingRepository.removeListSetting(lstWTBonusPaySettingDeleteCommand.stream()
				.map(c -> toWorkingTimesheetBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingDeleteCommand wtBonusPaySettingDeleteCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(
				wtBonusPaySettingDeleteCommand.getCompanyId().toString(),
				wtBonusPaySettingDeleteCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());

	}

}
