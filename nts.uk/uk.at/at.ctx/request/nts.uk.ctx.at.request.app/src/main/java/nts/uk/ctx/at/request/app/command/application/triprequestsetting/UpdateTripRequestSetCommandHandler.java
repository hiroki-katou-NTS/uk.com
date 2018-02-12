package nts.uk.ctx.at.request.app.command.application.triprequestsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSetRepository;

@Stateless
@Transactional
public class UpdateTripRequestSetCommandHandler extends CommandHandler<TripRequestSetCommand>{
	@Inject
	private TripRequestSetRepository tripRep;

	@Override
	protected void handle(CommandHandlerContext<TripRequestSetCommand> context) {
		TripRequestSetCommand data = context.getCommand();
		Optional<TripRequestSet> trip = tripRep.findByCid();
		TripRequestSet tripRequest = TripRequestSet.createFromJavaType(data.getCompanyId(), 
				data.getComment1(), "", 0, data.getComment2(), 
				"", 0, data.getWorkType(), data.getWorkChange(), 
				data.getWorkChangeTime(), data.getContractCheck(), data.getLateLeave());
		if(trip.isPresent()){
			tripRep.update(tripRequest);
			return;
		}
		tripRep.insert(tripRequest);
	}
}
