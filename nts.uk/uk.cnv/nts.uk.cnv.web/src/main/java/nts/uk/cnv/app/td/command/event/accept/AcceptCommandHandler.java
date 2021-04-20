package nts.uk.cnv.app.td.command.event.accept;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.AddedResultDto;
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.accept.AcceptEventRepository;
import nts.uk.cnv.dom.td.event.accept.AcceptService;
import nts.uk.cnv.dom.td.event.accept.AcceptedResult;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.order.OrderEvent;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Stateless
public class AcceptCommandHandler extends CommandHandlerWithResult<AcceptCommand, AddedResultDto> {
	@Inject
	private AlterationRepository alterationRepository;
	@Inject
	private AlterationSummaryRepository alterationSummaryRepository;
	@Inject
	private AcceptEventRepository acceptEventRepo;
	@Inject
	private SnapshotRepository snapshotRepository;
	
	@Override
	protected AddedResultDto handle(CommandHandlerContext<AcceptCommand> context) {
		
		AcceptService.Require require = EmbedStopwatch.embed(new RequireImpl());
		
		AcceptCommand command = context.getCommand();
		AcceptedResult result = AcceptService.accept(
				require,
				command.getDeliveryEventId(),
				command.getUserName());

		if(result.hasError()) {
			return AddedResultDto.fail(result.getErrorList());
		}

		if(result.getAtomTask().isPresent()) {
			transaction.execute(() -> {
				result.getAtomTask().get().run();
			});
		}

		return AddedResultDto.success(result.getEventId().get());
	}

	@RequiredArgsConstructor
	private class RequireImpl implements AcceptService.Require {
		
		@Override
		public Optional<String> getNewestAcceptId() {
			return acceptEventRepo.getNewestAcceptId();
		}
		@Override
		public void regist(AcceptEvent orderEvent) {
			acceptEventRepo.regist(orderEvent);
		}
		@Override
		public void registSchemaSnapShot(SchemaSnapshot schema) {
			snapshotRepository.regist(schema);
		}
		@Override
		public void registTableSnapShot(String snapshotId, List<TableDesign> tablesSnapshot) {
			snapshotRepository.regist(snapshotId, tablesSnapshot);
		}
		@Override
		public List<TableSnapshot> getTablesLatest() {
			return snapshotRepository.getTablesLatest();
		}
		@Override
		public List<Alteration> getAlterationsBy(List<String> alterIds) {
			return alterationRepository.gets(alterIds);
		}
		@Override
		public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
			return alterationSummaryRepository.getByTable(tableId, devProgress);
		}
		@Override
		public List<AlterationSummary> getByEvent(String deliveryEventId, DevelopmentProgress progress) {
			return alterationSummaryRepository.getByEvent(deliveryEventId, progress);
		}
		@Override
		public List<OrderEvent> getOrderEventByAlter(List<String> alters) {
			throw new RuntimeException("検収時に発注時のポリシーを確認しようとしています。ソースやデータを見直してください。");
		}
		@Override
		public List<DeliveryEvent> getDeliveryEventByAlter(List<String> alters) {
			throw new RuntimeException("検収時に納品時のポリシーを確認しようとしています。ソースやデータを見直してください。");
		}
		@Override
		public List<AcceptEvent> getAcceptEventByAlter(List<String> alters) {
			return acceptEventRepo.getByAlter(alters);
		}
	};
}
