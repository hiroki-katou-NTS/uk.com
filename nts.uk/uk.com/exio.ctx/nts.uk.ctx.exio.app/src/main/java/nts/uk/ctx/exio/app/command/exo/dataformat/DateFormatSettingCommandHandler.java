package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ItemType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DateFormatSettingCommandHandler extends CommandHandler<DateFormatSettingCommand> {

	@Inject
	private DateFormatSetRepository dateFormatSetRepository;

	@Override
	protected void handle(CommandHandlerContext<DateFormatSettingCommand> context) {
		DateFormatSettingCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		DateFormatSet dateFormatSetting = new DateFormatSet(ItemType.DATE.value, cid,
				command.getNullValueSubstitution(), command.getFixedValue(), command.getValueOfFixedValue(),
				command.getValueOfNullValueSubs(), command.getFormatSelection());

		Optional<DateFormatSet> dateFormatSets = dateFormatSetRepository.getDateFormatSetByCid(cid);
		if (dateFormatSets.isPresent()) {
			dateFormatSetRepository.update(dateFormatSetting);
		} else {
			dateFormatSetRepository.add(dateFormatSetting);
		}
	}
}
