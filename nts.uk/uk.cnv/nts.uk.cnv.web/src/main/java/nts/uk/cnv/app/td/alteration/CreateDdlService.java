package nts.uk.cnv.app.td.alteration;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.app.td.finder.event.DeliveryEventFinder;
import nts.uk.cnv.app.td.finder.event.OrderEventFinder;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.CreateAlterationDdlService;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

@Stateless
public class CreateDdlService {
	@Inject
	protected AlterationRepository alterRepo;
	@Inject
	protected OrderEventFinder orderEventFinder;
	@Inject
	protected DeliveryEventFinder deliveryEventFinder;
	@Inject
	protected SnapshotRepository snapshotRepo;
	@Inject
	private CreateAlterationDdlService service;

	public String createByOrderEvent(String orderId, DatabaseType type) {
		CreateByOrderEventRequireImpl require = new CreateByOrderEventRequireImpl();

		return service.createByOrderEvent(require, orderId, type);
	}

	public String createByDeliveryEvent(String deliveryId, DatabaseType type) {
		CreateByDeliveryEventRequireImpl require = new CreateByDeliveryEventRequireImpl();
		return service.createByDeliveryEvent(require, deliveryId, type);
	}

	private class CreateByOrderEventRequireImpl extends BaseRequireImpl implements CreateAlterationDdlService.CreateByOrderEventRequire{
		@Override
		public List<AlterationSummary> getAlterSummaryBy(String orderId) {
			return orderEventFinder.getBy(orderId);
		}
	}

	private class CreateByDeliveryEventRequireImpl extends BaseRequireImpl implements CreateAlterationDdlService.CreateByDeliveryEventRequire{
		@Override
		public List<AlterationSummary> getAlterSummaryBy(String deliveryId) {
			return deliveryEventFinder.getBy(deliveryId);
		}
	}

	private class BaseRequireImpl implements CreateAlterationDdlService.Require{
		@Override
		public SchemaSnapshot getSchemaLatest() {
			return snapshotRepo.getSchemaLatest().get();
		}

		@Override
		public Optional<TableSnapshot> getSnapshot(String snapshotId, String tableId) {
			return snapshotRepo.getTable(snapshotId, tableId);
		}

		@Override
		public Alteration getAlter(String alterationId) {
			return alterRepo.get(alterationId);
		}
	}
}