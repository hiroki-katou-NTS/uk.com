package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionCategoryTable;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionCategoryTableRepository;

@Stateless
public class RegistConversionCategoryCommandHandler extends CommandHandler<RegistConversionCategoryCommand>{

	@Inject
	ConversionCategoryTableRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistConversionCategoryCommand> context) {
		RegistConversionCategoryCommand command = context.getCommand();

		repository.delete(command.getCategory());

		int seq = 0;
		for(String table : command.getTables()) {
			repository.regist(new ConversionCategoryTable(command.getCategory(), table, seq));
			seq++;
		}
	}

}
