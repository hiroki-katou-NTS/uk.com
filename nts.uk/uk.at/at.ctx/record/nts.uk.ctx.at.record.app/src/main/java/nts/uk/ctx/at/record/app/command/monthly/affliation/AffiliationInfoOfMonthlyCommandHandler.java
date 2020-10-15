package nts.uk.ctx.at.record.app.command.monthly.affliation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthlyRepository;

@Stateless
public class AffiliationInfoOfMonthlyCommandHandler extends CommandFacade<AffiliationInfoOfMonthlyCommand> {

	@Inject
	private AffiliationInfoOfMonthlyRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AffiliationInfoOfMonthlyCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
