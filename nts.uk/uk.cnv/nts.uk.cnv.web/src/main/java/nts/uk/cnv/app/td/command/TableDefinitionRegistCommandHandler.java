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
import nts.uk.cnv.dom.td.alteration.SaveAlteration;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

@Stateless
public class TableDefinitionRegistCommandHandler extends CommandHandler<TableDefinitionRegistCommand> {

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

		val meta = new AlterationMetaData(
				command.getUserName(),
				command.getComment());

		val require = new RequireImpl(alterationRepo, snapshotRepo);
		val saving = SaveAlteration.save(
				require,
				command.getFeatureId(),
				tableInfo.getId(),
				meta,
				Optional.of(td));
		
		transaction.execute(saving);
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
				"",
				cds,
				TableConstraints.empty()
			);
		return td;
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements SaveAlteration.Require {
		private final AlterationRepository alterationRepo;
		private final SnapshotRepository snapshotRepo;

		@Override
		public TableSnapshot getNewestSnapshot(String tableId) {
			return null;
			//return snapshotRepo.getNewest(tableId);
		}

		@Override
		public List<Alteration> getUnaccepted(String tableId) {
			return alterationRepo.getUnaccepted(tableId);
		}

		@Override
		public void add(Alteration alt) {
			alterationRepo.insert(alt);
		}
	};
}
