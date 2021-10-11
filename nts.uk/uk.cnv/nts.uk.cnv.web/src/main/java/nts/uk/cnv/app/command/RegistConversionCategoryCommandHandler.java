package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.core.dom.TableIdentity;
import nts.uk.cnv.core.dom.conversiontable.ConversionCategoryTable;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;

@Stateless
public class RegistConversionCategoryCommandHandler extends CommandHandler<RegistConversionCategoryCommand>{

	@Inject
	ConversionCategoryTableRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistConversionCategoryCommand> context) {
		RegistConversionCategoryCommand command = context.getCommand();

		repository.delete(command.getCategory());

		int seq = 0;
		for(TableIdentity table : command.getTables()) {
			repository.regist(new ConversionCategoryTable(command.getCategory(), table, seq));
			seq++;
		}
	}

}
