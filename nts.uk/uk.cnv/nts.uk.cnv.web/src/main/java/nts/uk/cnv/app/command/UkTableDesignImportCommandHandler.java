package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.JSQLParserException;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.service.DDLImportService;
import nts.uk.cnv.dom.td.tabledesign.Snapshot;
import nts.uk.cnv.dom.td.tabledesign.UkTableDesignRepository;

@Stateless
public class UkTableDesignImportCommandHandler extends CommandHandler<UkTableDesignImportCommand> {

	@Inject
	UkTableDesignRepository tableDesignRepository;

	@Override
	protected void handle(CommandHandlerContext<UkTableDesignImportCommand> context) {

		UkTableDesignImportCommand command = context.getCommand();
		RequireImpl require = new RequireImpl(tableDesignRepository);

		transaction.execute(() -> {
			AtomTask at;
			try {
				at = DDLImportService.regist(
						command.getBranch(), command.getDate(),
						require, command.getCreateTableSql(), command.getCreateIndexSql(), command.getCommentSql(), command.getType());
			} catch (JSQLParserException e) {
				throw new BusinessException(new RawErrorMessage("SQL文解析に失敗しました：" + e.getCause().toString()));
			}
			at.run();
		});
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements DDLImportService.Require {

		private final UkTableDesignRepository tableDesignRepository;

		@Override
		public void regist(Snapshot tableDesign) {
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
