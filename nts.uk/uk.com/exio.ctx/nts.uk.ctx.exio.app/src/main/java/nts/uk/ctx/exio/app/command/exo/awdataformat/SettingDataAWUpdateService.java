package nts.uk.ctx.exio.app.command.exo.awdataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.AwDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;

@Stateless
@Transactional
public class SettingDataAWUpdateService extends CommandHandler<AWDataFormatCommand> {

	@Inject
	AwDataFormatSetRepository awDataFormatSetRepository;

	@Override
	protected void handle(CommandHandlerContext<AWDataFormatCommand> context) {
		AWDataFormatCommand command = context.getCommand();
		AwDataFormatSet awDataFormatSet = new AwDataFormatSet(ItemType.ATWORK.value, command.getCid(),
				command.getClosedOutput(), command.getAbsenceOutput(), command.getFixedValue(),
				command.getValueOfFixedValue(), command.getAtWorkOutput(), command.getRetirementOutput());
		awDataFormatSetRepository.update(awDataFormatSet);
	}
}
