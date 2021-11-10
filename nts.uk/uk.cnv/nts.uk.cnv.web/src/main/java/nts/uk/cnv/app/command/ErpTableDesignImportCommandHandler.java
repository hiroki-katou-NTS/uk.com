//package nts.uk.cnv.app.command;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import net.sf.jsqlparser.JSQLParserException;
//import nts.arc.error.BusinessException;
//import nts.arc.error.RawErrorMessage;
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.arc.task.tran.AtomTask;
//import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;
//
//@Stateless
//public class ErpTableDesignImportCommandHandler extends CommandHandler<ErpTableDesignImportCommand> {
//
//	@Inject
//	ErpTableDesignRepository tableDesignRepository;
//
//	@Inject
//	DDLImportService<ErpTableDesign, ColumnDesign> service;
//
//	@Override
//	protected void handle(CommandHandlerContext<ErpTableDesignImportCommand> context) {
//
//		ErpTableDesignImportCommand command = context.getCommand();
//		RequireImpl require = new RequireImpl(tableDesignRepository);
//		transaction.execute(() -> {
//			AtomTask at;
//			try {
//				at = service.register(
//						require, command.getCreateTableSql(), command.getCreateIndexSql(), command.getCommentSql(), command.getType());
//			} catch (JSQLParserException e) {
//				throw new BusinessException(new RawErrorMessage("SQL文解析に失敗しました：" + e.getCause().toString()));
//			}
//			at.run();
//		});
//	}

//	@RequiredArgsConstructor
//	private static class RequireImpl implements DDLImportService.Require<ErpTableDesign, ColumnDesign> {
//
//		private final ErpTableDesignRepository tableDesignRepository;
//
//		@Override
//		public void regist(ErpTableDesign tableDesign) {
//			boolean exists = tableDesignRepository.exists(tableDesign.getName());
//			if (exists) {
//				tableDesignRepository.update(tableDesign);
//			}
//			else {
//				tableDesignRepository.insert(tableDesign);
//			}
//		}
//
//		@Override
//		public ColumnDesign createColumnDesign(String columnId, String columnName, DataType type, int maxLength,
//				int scale, boolean nullable, String defaultValue, String check, String columnComment, int dispOrder) {
//			TableDefineType ukDefine = new UkDataType();
//			return new ColumnDesign(
//					columnId,
//					columnName,
//					ukDefine.dataType(type, maxLength, scale),
//					nullable,
//					defaultValue,
//					columnComment,
//					dispOrder
//				);
//		}
//
//		@Override
//		public ErpTableDesign createTableDesign(String tableName, String tableComment, List<ColumnDesign> columns) {
//			return new ErpTableDesign(tableName, columns);
//		}
//	}
//}
