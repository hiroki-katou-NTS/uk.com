package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteGrantDayRelationshipCommandHandler extends CommandHandler<DeleteGrantDayRelationshipCommand> {

	@Inject
	private GrantDayRelationshipRepository gDRelpRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteGrantDayRelationshipCommand> context) {

		String companyId = AppContexts.user().companyId();
		DeleteGrantDayRelationshipCommand cmd = context.getCommand();
		int sHENo = cmd.getSpecialHolidayEventNo();

		List<GrantDayRelationship> relpItems = this.gDRelpRepo.findBySHENo(companyId, sHENo);
		if (relpItems.size() == 1) {
			this.gDRelpRepo.deletePerRelp(companyId, sHENo);
		}

		this.gDRelpRepo.deleteRelp(sHENo, cmd.getRelationshipCd(), companyId);

	}

}
