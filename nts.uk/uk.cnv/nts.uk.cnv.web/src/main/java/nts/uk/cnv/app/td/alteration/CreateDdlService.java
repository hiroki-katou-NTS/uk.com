package nts.uk.cnv.app.td.alteration;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.app.td.finder.event.OrderEventFinder;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.CreateAlterationDdlService;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

@Stateless
public class CreateDdlService {
	@Inject
	protected AlterationRepository alterRepo;
	@Inject
	protected OrderEventFinder orderEventFinder;
	@Inject
	protected SnapshotRepository snapshotRepo;
	@Inject
	private CreateAlterationDdlService service;

	public String createByOrderEvent(String orderId) {
		RequireImpl require = new RequireImpl();

		return service.createByOrderEvent(require, orderId);
	}

	private class RequireImpl implements CreateAlterationDdlService.Require{
		@Override
		public List<AlterationSummary> getAlterSummaryBy(String orderId) {
			return orderEventFinder.getBy(orderId);
		}

		@Override
		public SchemaSnapshot getSchemaLatest() {
			return snapshotRepo.getSchemaLatest().get();
		}

		@Override
		public TableSnapshot getSnapshot(String snapshotId, String tableId) {
			return snapshotRepo.getTable(snapshotId, tableId).get();
		}

		@Override
		public Alteration getAlter(String alterationId) {
			return alterRepo.get(alterationId);
		}

	}
}