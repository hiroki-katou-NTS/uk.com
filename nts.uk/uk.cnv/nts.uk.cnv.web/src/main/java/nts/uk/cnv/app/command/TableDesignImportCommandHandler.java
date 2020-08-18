package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.JSQLParserException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.service.TableDesignImportService;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.TableDesignRepository;

@Stateless
public class TableDesignImportCommandHandler extends CommandHandler<TableDesignImportCommand> {

	@Inject
	TableDesignRepository tableDesignRepository;

	@Override
	protected void handle(CommandHandlerContext<TableDesignImportCommand> context) {

		TableDesignImportCommand command = context.getCommand();
		
		RequireImpl require = new RequireImpl(tableDesignRepository);

		transaction.execute(() -> {
			try {
				TableDesignImportService.regist(require, command.getCreateTableSql());
			} catch (JSQLParserException e) {
				throw BusinessException.takeFrom(e)
					.orElse(new BusinessException("SQL文解析に失敗しました"));
			}
		});
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements TableDesignImportService.Require {

		private final TableDesignRepository tableDesignRepository;
		
		@Override
		public void regist(TableDesign tableDesign) {
			boolean exists = tableDesignRepository.exists(tableDesign.getName());
			if (exists) {
				tableDesignRepository.update(tableDesign);
			}
			else {
				tableDesignRepository.insert(tableDesign);
			}
		}
	};
}
