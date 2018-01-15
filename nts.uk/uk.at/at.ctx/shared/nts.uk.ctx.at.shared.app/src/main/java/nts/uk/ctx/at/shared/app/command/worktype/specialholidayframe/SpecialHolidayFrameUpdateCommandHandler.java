package nts.uk.ctx.at.shared.app.command.worktype.specialholidayframe;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class SpecialHolidayFrameUpdateCommandHandler extends CommandHandler<SpecialHolidayFrameCommand> {
	@Inject
	private SpecialHolidayFrameRepository specialHolidayFrameRepository;

	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayFrameCommand> context) {
		SpecialHolidayFrameCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<SpecialHolidayFrame> data = specialHolidayFrameRepository.findHolidayFrameByCode(companyId, command.getSpecialHdFrameNo());
		if (!data.isPresent()) {
			throw new RuntimeException("Absence Frame Not Found");
		}
		
		SpecialHolidayFrame domain = command.toDomain();
		specialHolidayFrameRepository.update(domain);
	}
}
