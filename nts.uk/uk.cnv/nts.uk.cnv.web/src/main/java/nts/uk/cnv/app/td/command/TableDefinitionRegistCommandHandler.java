package nts.uk.cnv.app.td.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.service.TableDesignService;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.tabledesign.SnapshotRepository;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@Stateless
public class TableDefinitionRegistCommandHandler extends CommandHandler<TableDefinitionRegistCommand> {
	@Inject
	private TableDesignService tdService;

	@Inject
	private AlterationRepository alterationRepo;

	@Inject
	private SnapshotRepository snapshotRepo;

	@Override
	protected void handle(CommandHandlerContext<TableDefinitionRegistCommand> context) {
		val command = context.getCommand();
		val columns = command.getTd().getColumns();
		val tableInfo = command.getTd().getTableInfo();
		TableDesign td = createTd(columns, tableInfo);

		AlterationMetaData meta = new AlterationMetaData(
				command.getFeatureId(),
				command.getUserName(),
				command.getComment());

		RequireImpl require = new RequireImpl(alterationRepo, snapshotRepo);
		transaction.execute(() -> {
			AtomTask at = tdService.alter(
					require,
					td.getId(),
					meta,
					Optional.of(td));
			at.run();
		});
	}

	private TableDesign createTd(final java.util.List<nts.uk.cnv.ws.table.column.ColumnDefinitionDto> columns,
			final nts.uk.cnv.ws.table.TableInfoDto tableInfo) {
		List<ColumnDesign> cds = columns.stream()
				.map(dtoColumn -> new ColumnDesign(
						dtoColumn.getId(),
						dtoColumn.getName(),
						dtoColumn.getNameJp(),
						new DefineColumnType(
							dtoColumn.getType().getDataType(),
							dtoColumn.getType().getLength(),
							dtoColumn.getType().getScale(),
							dtoColumn.getType().isNullable(),
							dtoColumn.getType().getDefaultValue(),
							dtoColumn.getType().getCheck()),
						dtoColumn.getComment(),
						columns.indexOf(dtoColumn)
					))
				.collect(Collectors.toList());

		TableDesign td = new TableDesign(
				tableInfo.getId(),
				tableInfo.getName(),
				tableInfo.getNameJp(),
				cds,
				new ArrayList<>()
			);
		return td;
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements TableDesignService.Require {
		private final AlterationRepository alterationRepo;
		private final SnapshotRepository snapshotRepo;

		@Override
		public Optional<TableDesign> getNewest(String tableId) {
			Optional<TableDesign> ss =snapshotRepo.getNewest(tableId);
			List<Alteration> alterationList = alterationRepo.getUnaccepted(tableId);
			TableDesignBuilder builder = ss.isPresent()
					? new TableDesignBuilder(ss.get())
					: new TableDesignBuilder();

			alterationList.stream().forEach(alt ->{
				alt.apply(builder);
			});

			return builder.build();
		}

		@Override
		public void add(Alteration alt) {
			alterationRepo.insert(alt);
		}
	};
}
