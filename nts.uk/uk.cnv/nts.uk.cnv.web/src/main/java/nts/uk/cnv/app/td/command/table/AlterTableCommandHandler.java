package nts.uk.cnv.app.td.command.table;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.SaveAlteration;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

public class AlterTableCommandHandler extends CommandHandler<AlterTableCommand> {

	@Inject
	TransactionService transaction;
	
	@Override
	protected void handle(CommandHandlerContext<AlterTableCommand> context) {
		
		val command = context.getCommand();
		val require = new RequireImpl();
		
		val saving = saveAlter(command, require);
		
		transaction.execute(saving);
	}

	private static AtomTask saveAlter(
			AlterTableCommand command,
			AlterTableCommandHandler.RequireImpl require) {
		
		if (command.isNewTable()) {
			return SaveAlteration.createTable(
					require,
					command.getFeatureId(),
					command.getMetaData(),
					command.getTableDesign().toDomain());
		} else {
			return SaveAlteration.alterTable(
					require,
					command.getFeatureId(),
					command.getMetaData(),
					command.getTableDesign().getLastAlterId(),
					command.getTableDesign().toDomain());
		}
	}
	
	@Inject
	SnapshotRepository snapshotRepo;
	
	@Inject
	AlterationRepository alterRepo;

	private class RequireImpl implements SaveAlteration.Require {

		@Override
		public Optional<SchemaSnapshot> getSchemaSnapsohtLatest() {
			return snapshotRepo.getSchemaLatest();
		}

		@Override
		public Optional<TableSnapshot> getTableSnapshot(String snapshotId, String tableId) {
			return snapshotRepo.getTable(snapshotId, tableId);
		}

		@Override
		public List<Alteration> getAlterationsOfTable(String tableId, DevelopmentProgress progress) {
			return alterRepo.getTable(tableId, progress);
		}

		@Override
		public void save(Alteration alter) {
			alter.toString();
			//alterRepo.insert(alter);
		}
		
	}
}
