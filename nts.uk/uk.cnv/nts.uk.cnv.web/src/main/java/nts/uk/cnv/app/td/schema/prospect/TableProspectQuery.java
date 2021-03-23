package nts.uk.cnv.app.td.schema.prospect;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.prospect.definition.GenerateTableProspect;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TableProspectQuery {

	@Inject
	SnapshotRepository snapshotRepo;
	
	@Inject
	AlterationRepository alterRepo;
	
	public Optional<TableProspect> get(String tableId) {
		
		val require = new RequireImpl();
		
		return GenerateTableProspect.generate(require, tableId);
		
	}
	
	private class RequireImpl implements GenerateTableProspect.Require {

		@Override
		public Optional<SchemaSnapshot> getSchemaSnapshotLatest() {
			return snapshotRepo.getSchemaLatest();
		}

		@Override
		public Optional<TableSnapshot> getTableSnapshot(String snapshotId, String tableId) {
			return snapshotRepo.getTable(snapshotId, tableId);
		}

		@Override
		public List<Alteration> getAlterations(String tableId, DevelopmentProgress progress) {
			return alterRepo.getTable(tableId, progress);
		}
		
	}
}
