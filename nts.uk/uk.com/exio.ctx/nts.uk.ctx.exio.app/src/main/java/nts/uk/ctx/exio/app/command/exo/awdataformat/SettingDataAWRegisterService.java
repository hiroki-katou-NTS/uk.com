package nts.uk.ctx.exio.app.command.exo.awdataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SettingDataAWRegisterService extends CommandHandler<AWDataFormatCommand> {

	@Inject
	DataFormatSettingRepository awDataFormatSetRepository;

	@Override
	protected void handle(CommandHandlerContext<AWDataFormatCommand> context) {
		AWDataFormatCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		AwDataFormatSet awDataFormatSet = new AwDataFormatSet(ItemType.AT_WORK_CLS.value, cid,
				command.getClosedOutput(), command.getAbsenceOutput(), command.getFixedValue(),
				command.getValueOfFixedValue(), command.getAtWorkOutput(), command.getRetirementOutput());
		Optional<AwDataFormatSet> awDataFormatSetCheck = awDataFormatSetRepository.getAwDataFormatSetById(cid);
		if (!awDataFormatSetCheck.isPresent()) {
			awDataFormatSetRepository.add(awDataFormatSet);
		} else {
			awDataFormatSetRepository.update(awDataFormatSet);
		}
	}
}
