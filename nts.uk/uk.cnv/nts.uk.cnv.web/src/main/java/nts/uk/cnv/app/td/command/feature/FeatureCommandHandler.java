package nts.uk.cnv.app.td.command.feature;

import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.td.feature.Feature;
import nts.uk.cnv.dom.td.feature.FeatureRepository;
import nts.uk.cnv.dom.td.feature.FeatureService;

public class FeatureCommandHandler extends CommandHandler<FeatureCommand> {
	
	@Inject
	FeatureRepository featureRepo;
		
	@Inject
	private FeatureService featureService;

	@Override
	protected void handle(CommandHandlerContext<FeatureCommand> context) {
		val command = context.getCommand();
		val require = new RequireImpl();
		val atomTask = featureService.createFeature(require, command.getFeatureName(), command.getDescription());
		transaction.execute(() -> {
			atomTask.run();
		});
	}
	
	private class RequireImpl implements FeatureService.Require{

		@Override
		public void regist(Feature feature) {
			featureRepo.insert(feature);
		}
	}
}
