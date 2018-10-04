package nts.uk.ctx.at.record.app.command.monthly.absenceleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainDataRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class AbsenceLeaveRemainMonthlyCommandHandler extends CommandFacade<AbsenceLeaveRemainMonthlyCommand> {

	@Inject
	private AbsenceLeaveRemainDataRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AbsenceLeaveRemainMonthlyCommand> context) {
		if(context.getCommand().getData().isHaveData()) {
			repo.persistAndUpdate(context.getCommand().toDomain());
		}
		
	}
}
