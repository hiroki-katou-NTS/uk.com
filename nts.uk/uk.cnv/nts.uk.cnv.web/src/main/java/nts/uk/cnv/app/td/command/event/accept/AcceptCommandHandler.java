package nts.uk.cnv.app.td.command.event.accept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.AcceptEvent;
import nts.uk.cnv.dom.td.event.AcceptEventRepository;
import nts.uk.cnv.dom.td.event.AcceptService;
import nts.uk.cnv.dom.td.event.AcceptedResult;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

@Stateless
public class AcceptCommandHandler extends CommandHandlerWithResult<AcceptCommand, List<AlterationSummary>> {
	@Inject
	private AlterationSummaryRepository alterationSummaryRepo;
	@Inject
	private AcceptEventRepository acceptEventRepo;
	@Inject
	private AcceptService service;
	@Inject
	private SnapshotRepository snapshotRepository;
	
	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<AcceptCommand> context) {
		RequireImpl require = new RequireImpl(alterationSummaryRepo, acceptEventRepo,snapshotRepository);
		AcceptCommand command = context.getCommand();
		AcceptedResult result = service.accept(
				require,
				command.getDeliveryEventId(),
				command.getName(),
				command.getUserName());

		if(result.hasError()) {
			return result.getErrorList();
		}

		if(result.getAtomTask().isPresent()) {
			transaction.execute(() -> {
				result.getAtomTask().get().run();
			});
		}

		return new ArrayList<>();
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements AcceptService.Require {
		private final AlterationSummaryRepository alterationSummaryRepo;
		private final AcceptEventRepository acceptEventRepo;
		private final SnapshotRepository snapshotRepository;
		
		@Override
		public Optional<String> getNewestAcceptId() {
			return acceptEventRepo.getNewestAcceptId();
		}
		@Override
		public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByFeature(featureId, devProgress);
		}
		@Override
		public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByTable(tableId, devProgress);
		}
		@Override
		public void regist(AcceptEvent orderEvent) {
			acceptEventRepo.regist(orderEvent);
		}
		@Override
		public List<AlterationSummary> getEvent(String deliveryEventId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByEvent(deliveryEventId, devProgress.getBaseline());
		}
		@Override
		public void registSchemaSnapShot(SchemaSnapshot schema) {
			snapshotRepository.regist(schema);
		}
		@Override
		public void registTableSnapShot(List<TableSnapshot> table) {
			
		}
		@Override
		public List<Alteration> gets(List<String> alterationIds) {
			return null;
		}
		@Override
		public List<TableSnapshot> getTablesLatest() {
			return snapshotRepository.getTablesLatest();
		}
	};
}
