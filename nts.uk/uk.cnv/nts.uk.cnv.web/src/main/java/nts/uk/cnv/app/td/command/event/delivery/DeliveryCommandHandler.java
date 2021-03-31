package nts.uk.cnv.app.td.command.event.delivery;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.AddedResultDto;
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveredResult;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEventRepository;
import nts.uk.cnv.dom.td.event.delivery.DeliveryService;
import nts.uk.cnv.dom.td.event.order.OrderEvent;

@Stateless
public class DeliveryCommandHandler extends CommandHandlerWithResult<DeliveryCommand, AddedResultDto> {
	@Inject
	private AlterationSummaryRepository alterationSummaryRepo;

	@Inject
	private DeliveryEventRepository deliveryEventRepo;

	@Override
	protected AddedResultDto handle(CommandHandlerContext<DeliveryCommand> context) {
		RequireImpl require = new RequireImpl();
		DeliveryCommand command = context.getCommand();
		DeliveredResult result = DeliveryService.delivery(
				require,
				command.getFeatureId(),
				command.getName(),
				command.getUserName(),
				command.getAlterationIds());

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
	private class RequireImpl implements DeliveryService.Require {

		@Override
		public Optional<String> getNewestDeliveryId() {
			return deliveryEventRepo.getNewestDeliveryId();
		}
		@Override
		public void regist(DeliveryEvent deliveryEvent) {
			deliveryEventRepo.regist(deliveryEvent);
		}
		@Override
		public List<AlterationSummary> getByAlter(List<String> alterIds) {
			return alterationSummaryRepo.getByAlter(alterIds);
		}
		@Override
		public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByTable(tableId, devProgress);
		}
		@Override
		public List<OrderEvent> getOrderEventByAlter(List<String> alterations) {
			return null;
		}
		@Override
		public List<DeliveryEvent> getDeliveryEventByAlter(List<String> alterations) {
			return deliveryEventRepo.getByAlter(alterations);
		}
		@Override
		public List<AcceptEvent> getAcceptEventByAlter(List<String> alterations) {
			return null;
		}
	};
}
