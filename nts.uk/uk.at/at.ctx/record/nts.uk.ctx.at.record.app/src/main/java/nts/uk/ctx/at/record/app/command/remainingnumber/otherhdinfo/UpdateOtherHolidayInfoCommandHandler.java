package nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.otherholiday.OtherHolidayInfoService;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateOtherHolidayInfoCommandHandler extends CommandHandler<UpdateOtherHolidayInfoCommand>
implements PeregUpdateCommandHandler<UpdateOtherHolidayInfoCommand> {

	@Inject
	private OtherHolidayInfoService otherHolidayInfoService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00035";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateOtherHolidayInfoCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateOtherHolidayInfoCommand> context) {
		val command = context.getCommand();
		String cid = AppContexts.user().companyId();
		PublicHolidayRemain pubHD = new PublicHolidayRemain(cid, command.getEmployeeId(), command.getPubHdremainNumber());
		ExcessLeaveInfo exLeav = new ExcessLeaveInfo(cid, command.getEmployeeId(), command.getUseAtr().intValue(), command.getOccurrenceUnit().intValue(), command.getPaymentMethod().intValue());
		otherHolidayInfoService.updateOtherHolidayInfo(cid, pubHD, exLeav, command.getRemainNumber(), command.getRemainsLeft());
	}

}
