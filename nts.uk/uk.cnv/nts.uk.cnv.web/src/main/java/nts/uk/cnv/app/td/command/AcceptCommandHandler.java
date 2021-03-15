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
import nts.uk.cnv.dom.td.event.AcceptEvent;
import nts.uk.cnv.dom.td.event.AcceptEventRepository;
import nts.uk.cnv.dom.td.event.AcceptService;
import nts.uk.cnv.dom.td.event.AcceptedResult;

@Stateless
public class AcceptCommandHandler extends CommandHandlerWithResult<AcceptCommand, List<AlterationSummary>> {
	@Inject
	private AlterationRepository alterationRepo;

	@Inject
	private AcceptEventRepository acceptEventRepo;

	@Inject
	private AcceptService service;

	@Override
	protected List<AlterationSummary> handle(CommandHandlerContext<AcceptCommand> context) {
		RequireImpl require = new RequireImpl(alterationRepo, acceptEventRepo);
		AcceptCommand command = context.getCommand();
		AcceptedResult result = service.accept(
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
	private static class RequireImpl implements AcceptService.Require {
		private final AlterationRepository alterationRepo;
		private final AcceptEventRepository acceptEventRepo;

		@Override
		public Optional<String> getNewestAcceptId() {
			return acceptEventRepo.getNewestAcceptId();
		}
		@Override
		public List<AlterationSummary> getAllUnaccepted(String featureId) {
			return alterationRepo.getAllUnaccepted(featureId);
		}
		@Override
		public List<AlterationSummary> getOlderUnaccepted(String alterId) {
			return alterationRepo.getOlderUnaccepted(alterId);
		}
		@Override
		public void regist(AcceptEvent orderEvent) {
			acceptEventRepo.regist(orderEvent);
		}
	};
}
