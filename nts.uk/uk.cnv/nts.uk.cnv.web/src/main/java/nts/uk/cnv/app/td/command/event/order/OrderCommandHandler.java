package nts.uk.cnv.app.td.command.event.order;

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
import nts.uk.cnv.dom.td.event.OrderEvent;
import nts.uk.cnv.dom.td.event.OrderEventRepository;
import nts.uk.cnv.dom.td.event.OrderService;
import nts.uk.cnv.dom.td.event.OrderedResult;

@Stateless
public class OrderCommandHandler extends CommandHandlerWithResult<OrderCommand, List<AlterationSummary>> {
	@Inject
	private AlterationRepository alterationRepo;

	//@Inject
	private OrderEventRepository orderEventRepo;

	@Inject
	private OrderService service;

	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<OrderCommand> context) {
		RequireImpl require = new RequireImpl(alterationRepo, orderEventRepo);
		OrderCommand command = context.getCommand();
		OrderedResult result = service.order(
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
	private static class RequireImpl implements OrderService.Require {
		private final AlterationRepository alterationRepo;
		private final OrderEventRepository orderEventRepo;

		@Override
		public Optional<String> getNewestOrderId() {
			return orderEventRepo.getNewestOrderId();
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
		public void regist(OrderEvent orderEvent) {
			orderEventRepo.regist(orderEvent);
		}

	};
}
