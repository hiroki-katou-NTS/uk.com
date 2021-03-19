package nts.uk.cnv.app.td.command.event.order;

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
import nts.uk.cnv.dom.td.event.OrderEvent;
import nts.uk.cnv.dom.td.event.OrderEventRepository;
import nts.uk.cnv.dom.td.event.OrderService;
import nts.uk.cnv.dom.td.event.OrderedResult;

@Stateless
public class OrderCommandHandler extends CommandHandlerWithResult<OrderCommand, List<AlterationSummary>> {
	@Inject
	protected AlterationSummaryRepository alterationSummaryRepo;

	@Inject
	protected OrderEventRepository orderEventRepo;

	@Inject
	private OrderService service;

	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<OrderCommand> context) {
		RequireImpl require = new RequireImpl();
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
	private class RequireImpl implements OrderService.Require {

		@Override
		public Optional<String> getNewestOrderId() {
			return orderEventRepo.getNewestOrderId();
		}
		@Override
		public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByFeature(featureId);
		}
		@Override
		public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByTable(tableId, devProgress);
		}
		@Override
		public void regist(OrderEvent orderEvent) {
			orderEventRepo.regist(orderEvent);
		}

	};
}
