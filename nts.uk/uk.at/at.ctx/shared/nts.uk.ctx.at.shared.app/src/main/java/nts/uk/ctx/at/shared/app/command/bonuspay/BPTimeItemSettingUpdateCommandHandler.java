package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BPTimeItemSetting;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class BPTimeItemSettingUpdateCommandHandler extends CommandHandler<List<BPTimeItemSettingUpdateCommand>> {
	@Inject
	private BPTimeItemSettingRepository bpTimeItemSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemSettingUpdateCommand>> context) {
		List<BPTimeItemSettingUpdateCommand> bpTimeItemSettingUpdateCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		bpTimeItemSettingRepository.updateListSetting(bpTimeItemSettingUpdateCommand.stream()
				.map(c -> toBPTimeItemSettingDomain(c,companyId)).collect(Collectors.toList()));
	}

	private BPTimeItemSetting toBPTimeItemSettingDomain(BPTimeItemSettingUpdateCommand bpTimeItemSettingUpdateCommand,String companyId) {
		return BPTimeItemSetting.createFromJavaType(companyId,
				bpTimeItemSettingUpdateCommand.timeItemNo,
				bpTimeItemSettingUpdateCommand.holidayCalSettingAtr,
				bpTimeItemSettingUpdateCommand.overtimeCalSettingAtr,
				bpTimeItemSettingUpdateCommand.worktimeCalSettingAtr,
				bpTimeItemSettingUpdateCommand.timeItemTypeAtr);
	}

}
