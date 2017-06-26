package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BPTimeItemSetting;
@Stateless
public class BPTimeItemSettingUpdateCommandHandler extends CommandHandler<List<BPTimeItemSettingUpdateCommand>> {
	@Inject
	private BPTimeItemSettingRepository bpTimeItemSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemSettingUpdateCommand>> context) {
		List<BPTimeItemSettingUpdateCommand> bpTimeItemSettingUpdateCommand = context.getCommand();
		bpTimeItemSettingRepository.updateListSetting(bpTimeItemSettingUpdateCommand.stream()
				.map(c -> toBPTimeItemSettingDomain(c)).collect(Collectors.toList()));
	}

	private BPTimeItemSetting toBPTimeItemSettingDomain(BPTimeItemSettingUpdateCommand bpTimeItemSettingUpdateCommand) {
		return BPTimeItemSetting.createFromJavaType(bpTimeItemSettingUpdateCommand.getCompanyId(),
				bpTimeItemSettingUpdateCommand.getTimeItemId(), bpTimeItemSettingUpdateCommand.holidayCalSettingAtr,
				bpTimeItemSettingUpdateCommand.overtimeCalSettingAtr,
				bpTimeItemSettingUpdateCommand.worktimeCalSettingAtr);
	}

}
