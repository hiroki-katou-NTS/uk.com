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
public class WTBonusPaySettingUpdateCommandHandler extends CommandHandler<List<WTBonusPaySettingUpdateCommand>> {
	@Inject
	private WTBonusPaySettingRepository wtBonusPaySettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<WTBonusPaySettingUpdateCommand>> context) {
		List<WTBonusPaySettingUpdateCommand> lstWTBonusPaySettingUpdateCommand = context.getCommand();
		this.wtBonusPaySettingRepository.updateListSetting(lstWTBonusPaySettingUpdateCommand.stream()
				.map(c -> toWorkingTimesheetBonusPaySettingDomain(c)).collect(Collectors.toList()));
	}

	private WorkingTimesheetBonusPaySetting toWorkingTimesheetBonusPaySettingDomain(
			WTBonusPaySettingUpdateCommand wtBonusPaySettingUpdateCommand) {
		return WorkingTimesheetBonusPaySetting.createFromJavaType(
				wtBonusPaySettingUpdateCommand.getCompanyId().toString(),
				wtBonusPaySettingUpdateCommand.getWorkingTimesheetCode().toString(),
				wtBonusPaySettingUpdateCommand.getBonusPaySettingCode().toString());

	}
}
