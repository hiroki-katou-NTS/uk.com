package nts.uk.ctx.exio.app.command.exi.codeconvert;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;

@Stateless
@Transactional
public class AddAcceptCdConvertCommandHandler extends CommandHandler<AcceptCdConvertCommand> {

	@Inject
	private AcceptCdConvertRepository repository;

	@Override
	public void handle(CommandHandlerContext<AcceptCdConvertCommand> context) {
		AcceptCdConvertCommand addCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		repository.add(new AcceptCdConvert(companyId, addCommand.getConvertCd(), addCommand.getConvertName(),
				addCommand.getAcceptWithoutSetting(), addCommand.getCdConvertDetails().stream().map(itemDetail -> {
					return new CdConvertDetails(companyId, itemDetail.getConvertCd(), itemDetail.getLineNumber(),
							itemDetail.getOutputItem(), itemDetail.getSystemCd());
				}).collect(Collectors.toList())));

	}
}
