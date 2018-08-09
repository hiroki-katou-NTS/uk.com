package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayPerRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantedDay;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.MorningHour;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveGrantDayRelationshipCommandHandler extends CommandHandler<SaveGrantDayRelationshipCommand> {

	@Inject
	private GrantDayRelationshipRepository gDRelpRepo;

	@Override
	protected void handle(CommandHandlerContext<SaveGrantDayRelationshipCommand> context) {

		String companyId = AppContexts.user().companyId();

		SaveGrantDayRelationshipCommand cmd = context.getCommand();
		if (cmd.isCreateNew()) {
			insertData(companyId, cmd);
		} else {
			updateData(companyId, cmd);
		}
	}

	private void updateData(String companyId, SaveGrantDayRelationshipCommand cmd) {
		int SHENo = cmd.getSpecialHolidayEventNo();
		GrantDayRelationship newDomain = new GrantDayRelationship(companyId, SHENo,
				new RelationshipCode(cmd.getRelationshipCd()), new GrantedDay(cmd.getGrantedDay()),
				new MorningHour(cmd.getMorningHour()));
		this.gDRelpRepo.updateRelp(companyId, newDomain);
	}

	private void insertData(String companyId, SaveGrantDayRelationshipCommand cmd) {
		int SHENo = cmd.getSpecialHolidayEventNo();

		Optional<GrantDayPerRelationship> perOpt = this.gDRelpRepo.findPerByCd(companyId, SHENo);

		if (!perOpt.isPresent()) {
			GrantDayPerRelationship newPerDomain = new GrantDayPerRelationship(companyId, SHENo, UseAtr.USE, new ArrayList<>());
			this.gDRelpRepo.insertPerRelp(newPerDomain);

		}
		GrantDayRelationship newDomain = new GrantDayRelationship(companyId, SHENo,
				new RelationshipCode(cmd.getRelationshipCd()), new GrantedDay(cmd.getGrantedDay()),
				new MorningHour(cmd.getMorningHour()));
		this.gDRelpRepo.insertRelp(newDomain);

	}

}
