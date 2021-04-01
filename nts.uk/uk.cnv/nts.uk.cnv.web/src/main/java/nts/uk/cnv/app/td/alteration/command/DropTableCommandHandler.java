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
import nts.arc.task.tran.TransactionService;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.SaveAlteration;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DropTableCommandHandler extends CommandHandler<AlterTableCommand> {
	
	@Inject
	TransactionService transaction;
	
	@Override
	protected void handle(CommandHandlerContext<AlterTableCommand> context) {
		
		val command = context.getCommand();
		val require = new RequireImpl();
		
		val saving = SaveAlteration.dropTable(
					require,
					command.getFeatureId(),
					command.getMetaData(),
					command.getTableDesign().getLastAlterId(),
					command.getTableDesign().getId());

		transaction.execute(saving);
	}

	@Inject
	SnapshotRepository snapshotRepo;
	
	@Inject
	AlterationRepository alterRepo;

	private class RequireImpl implements SaveAlteration.RequireAlterTable {

		@Override
		public Optional<SchemaSnapshot> getSchemaSnapshotLatest() {
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
			alterRepo.insert(alter);
		}
	}
}
