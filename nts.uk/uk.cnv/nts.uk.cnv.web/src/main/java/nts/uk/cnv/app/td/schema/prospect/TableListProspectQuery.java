package nts.uk.cnv.app.td.schema.prospect;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlteration;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.prospect.list.GenerateTableListProspect;
import nts.uk.cnv.dom.td.schema.prospect.list.TableListProspect;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TableListProspectQuery {
	
	@Inject
	SnapshotRepository snapshotRepo;

	public TableListProspect get() {
		
		val require = new RequireImpl();
		
		return GenerateTableListProspect.generate(require);
	}
	
	private class RequireImpl implements GenerateTableListProspect.Require {

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
			return Collections.emptyList();
		}

		
	}
}
