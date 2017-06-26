package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;
@Stateless
public class PSBonusPaySettingDeleteCommandHandler extends CommandHandler<List<PSBonusPaySettingDeleteCommand>> {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;
	@Override
	protected void handle(CommandHandlerContext<List<PSBonusPaySettingDeleteCommand>> context) {
	List<PSBonusPaySettingDeleteCommand> lstPSBonusPaySettingDeleteCommand = context.getCommand();
		this.psBonusPaySettingRepository.removeListSetting(lstPSBonusPaySettingDeleteCommand.stream()
				.map(c -> toPersonalBonusPaySettingDomain(c)).collect(Collectors.toList()));
		
	}
	private PersonalBonusPaySetting toPersonalBonusPaySettingDomain(
			PSBonusPaySettingDeleteCommand psBonusPaySettingDeleteCommand) {
		return PersonalBonusPaySetting.createFromJavaType(psBonusPaySettingDeleteCommand.getEmployeeId().toString(),
				psBonusPaySettingDeleteCommand.getBonusPaySettingCode().toString());
	}

}
