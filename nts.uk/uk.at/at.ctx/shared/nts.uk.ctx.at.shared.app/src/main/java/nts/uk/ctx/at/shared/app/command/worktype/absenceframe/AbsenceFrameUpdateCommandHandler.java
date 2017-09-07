package nts.uk.ctx.at.shared.app.command.worktype.absenceframe;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class AbsenceFrameUpdateCommandHandler extends CommandHandler<AbsenceFrameCommand> {
	@Inject
	private AbsenceFrameRepository absenceFrameRepository;

	@Override
	protected void handle(CommandHandlerContext<AbsenceFrameCommand> context) {
		AbsenceFrameCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<AbsenceFrame> data = absenceFrameRepository.findAbsenceFrameByCode(companyId, command.getAbsenceFrameNo());
		if (!data.isPresent()) {
			throw new RuntimeException("Absence Frame Not Found");
		}
		
		AbsenceFrame domain = command.toDomain();
		absenceFrameRepository.update(domain);
	}
}
