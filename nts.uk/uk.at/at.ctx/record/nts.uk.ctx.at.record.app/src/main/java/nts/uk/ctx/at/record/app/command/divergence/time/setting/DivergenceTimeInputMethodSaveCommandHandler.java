package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository;

@Stateless
public class DivergenceTimeInputMethodSaveCommandHandler extends CommandHandler<DivergenceTimeInputMethodSaveCommand> {

	/** The divergence time repo. */
	@Inject
	private DivergenceTimeRepository divTimeRepo;

	@Inject
	private DivergenceReasonInputMethodRepository divReasonInputRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DivergenceTimeInputMethodSaveCommand> context) {
		// get command
		DivergenceTimeInputMethodSaveCommand command = context.getCommand();

		// Convert to DivergenceTimeSaveCommand
		DivergenceTimeSaveCommand divTimeCommand = new DivergenceTimeSaveCommand(command.getDivergenceTimeNo(),
				command.getCompanyId(), command.getDivergenceTimeUseSet(), command.getDivergenceTimeName(),
				command.getDivergenceType(), command.isReasonInput(), command.isReasonSelect(),
				command.getAttendanceId().stream().map(e -> e.doubleValue()).collect(Collectors.toList()));

		// Convert to DivergenceReasonInputMethosSaveCommand

		DivergenceReasonInputMethodSaveCommand divReasonCommand = new DivergenceReasonInputMethodSaveCommand(
				command.getDivergenceTimeNo(), command.getCompanyId(), command.isDivergenceReasonInputed(),
				command.isDivergenceReasonSelected());
		// convert to domain

		DivergenceTime divTime = new DivergenceTime(divTimeCommand);
		DivergenceReasonInputMethod divReasonInput = new DivergenceReasonInputMethod(divReasonCommand);
		// update
		this.divTimeRepo.update(divTime);
		this.divReasonInputRepo.update(divReasonInput);
	}

}
