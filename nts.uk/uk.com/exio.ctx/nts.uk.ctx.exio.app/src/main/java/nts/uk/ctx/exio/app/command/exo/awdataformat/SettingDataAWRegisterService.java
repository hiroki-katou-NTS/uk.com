package nts.uk.ctx.exio.app.command.exo.awdataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ItemType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SettingDataAWRegisterService extends CommandHandler<AWDataFormatCommand> {

	@Inject
	AwDataFormatSetRepository awDataFormatSetRepository;

	@Override
	protected void handle(CommandHandlerContext<AWDataFormatCommand> context) {
		AWDataFormatCommand command = context.getCommand();
		AwDataFormatSet awDataFormatSet = new AwDataFormatSet(ItemType.ATWORK.value, command.getCid(),
				command.getClosedOutput(), command.getAbsenceOutput(), command.getFixedValue(),
				command.getValueOfFixedValue(), command.getAtWorkOutput(), command.getRetirementOutput());
		String cid = AppContexts.user().companyId();
		Optional<AwDataFormatSet> awDataFormatSetCheck = awDataFormatSetRepository
				.getAwDataFormatSetById(cid);
		if(!awDataFormatSetCheck.isPresent()){
		awDataFormatSetRepository.add(awDataFormatSet);
		}
		awDataFormatSetRepository.update(awDataFormatSet);
	}
}
