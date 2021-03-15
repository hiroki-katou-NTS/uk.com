package nts.uk.cnv.app.td.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.event.DeliveredResult;
import nts.uk.cnv.dom.td.event.DeliveryEvent;
import nts.uk.cnv.dom.td.event.DeliveryEventRepository;
import nts.uk.cnv.dom.td.event.DeliveryService;

@Stateless
public class DeliveryCommandHandler extends CommandHandlerWithResult<DeliveryCommand, List<AlterationSummary>> {
	@Inject
	private AlterationRepository alterationRepo;

	@Inject
	private DeliveryEventRepository deliveryEventRepo;

	@Inject
	private DeliveryService service;

	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<DeliveryCommand> context) {
		RequireImpl require = new RequireImpl(alterationRepo, deliveryEventRepo);
		DeliveryCommand command = context.getCommand();
		DeliveredResult result = service.delivery(
				require,
				command.getFeatureId(),
				command.getMeta(),
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
	private static class RequireImpl implements DeliveryService.Require {
		private final AlterationRepository alterationRepo;
		private final DeliveryEventRepository deliveryEventRepo;

		@Override
		public Optional<String> getNewestDeliveryId() {
			return deliveryEventRepo.getNewestDeliveryId();
		}
		@Override
		public List<AlterationSummary> getAllUndeliveled(String featureId) {
			return alterationRepo.getAllUndeliveled(featureId);
		}
		@Override
		public List<AlterationSummary> getOlderUndeliveled(String alterId) {
			return alterationRepo.getOlderUndeliveled(alterId);
		}
		@Override
		public void regist(DeliveryEvent deliveryEvent) {
			deliveryEventRepo.regist(deliveryEvent);
		}

	};
}
