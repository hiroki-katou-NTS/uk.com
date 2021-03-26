package nts.uk.cnv.app.td.command.event.delivery;

import java.util.ArrayList;
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
import nts.uk.cnv.dom.td.event.delivery.DeliveredResult;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEventRepository;
import nts.uk.cnv.dom.td.event.delivery.DeliveryService;

@Stateless
public class DeliveryCommandHandler extends CommandHandlerWithResult<DeliveryCommand, List<AlterationSummary>> {
	@Inject
	private AlterationSummaryRepository alterationSummaryRepo;

	@Inject
	private DeliveryEventRepository deliveryEventRepo;

	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<DeliveryCommand> context) {
		RequireImpl require = new RequireImpl();
		DeliveryCommand command = context.getCommand();
		DeliveredResult result = DeliveryService.delivery(
				require,
				command.getFeatureId(),
				command.getName(),
				command.getUserName(),
				command.getAlterationIds());

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
	private class RequireImpl implements DeliveryService.Require {

		@Override
		public Optional<String> getNewestDeliveryId() {
			return deliveryEventRepo.getNewestDeliveryId();
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
		public void regist(DeliveryEvent deliveryEvent) {
			deliveryEventRepo.regist(deliveryEvent);
		}
	};
}
