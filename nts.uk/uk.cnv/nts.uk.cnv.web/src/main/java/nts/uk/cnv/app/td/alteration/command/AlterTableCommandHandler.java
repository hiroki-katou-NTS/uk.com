package nts.uk.cnv.app.td.alteration.command;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.SaveAlteration;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlteration;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlterationRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AlterTableCommandHandler extends CommandHandler<AlterTableCommand> {

	@Inject
	TransactionService transaction;
	
	@Override
	protected void handle(CommandHandlerContext<AlterTableCommand> context) {
		
		val command = context.getCommand();
		
		Require require = new RequireImpl();
		
		val saving = saveAlter(command, require);
		
		transaction.execute(saving);
	}

	private AtomTask saveAlter(AlterTableCommand command, Require require) {
		
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
	
	@Inject
	SchemaAlterationRepository schemaAlterRepo;
	
	private static interface Require extends
		SaveAlteration.RequireCreateTable,
		SaveAlteration.RequireAlterTable {
	}
	
	private class RequireImpl implements Require {

		@Override
		public Optional<SchemaSnapshot> getSchemaSnapshotLatest() {
			return snapshotRepo.getSchemaLatest();
		}

		@Override
		public TableListSnapshot getTableListSnapshot(String snapsohtId) {
			return snapshotRepo.getTableList(snapsohtId);
		}

		@Override
		public List<SchemaAlteration> getSchemaAlteration(DevelopmentProgress progress) {
			return schemaAlterRepo.get(progress);
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
			alterRepo.insert(alter);
		}
	}
}
