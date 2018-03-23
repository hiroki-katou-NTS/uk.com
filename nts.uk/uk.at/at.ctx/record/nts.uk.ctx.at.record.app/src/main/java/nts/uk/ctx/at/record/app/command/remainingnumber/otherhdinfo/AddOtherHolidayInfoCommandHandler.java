package nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddOtherHolidayInfoCommandHandler extends CommandHandlerWithResult<AddOtherHolidayInfoCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddOtherHolidayInfoCommand> {

	@Inject
	private PublicHolidayRemainRepository publicHolidayRemainRepository;
	
	@Inject 
	private ExcessLeaveInfoRepository excessLeaveInfoRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00035";
	}

	@Override
	public Class<?> commandClass() {
		return AddOtherHolidayInfoCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddOtherHolidayInfoCommand> context) {
		val command = context.getCommand();
		
		PublicHolidayRemain pubHD = new PublicHolidayRemain(command.getEmployeeId(), command.getPubHdremainNumber());
		publicHolidayRemainRepository.add(pubHD);
		
		ExcessLeaveInfo exLeav = new ExcessLeaveInfo(command.getEmployeeId(), command.getUseAtr(), command.getOccurrenceUnit(), command.getPaymentMethod());
		excessLeaveInfoRepository.add(exLeav);
		
		return new PeregAddCommandResult(command.getEmployeeId());
	}

}
