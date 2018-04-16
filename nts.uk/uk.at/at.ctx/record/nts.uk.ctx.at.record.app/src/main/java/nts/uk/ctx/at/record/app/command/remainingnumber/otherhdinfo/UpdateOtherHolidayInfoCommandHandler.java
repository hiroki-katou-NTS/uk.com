package nts.uk.ctx.at.record.app.command.remainingnumber.otherhdinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateOtherHolidayInfoCommandHandler extends CommandHandler<AddOtherHolidayInfoCommand>
implements PeregUpdateCommandHandler<AddOtherHolidayInfoCommand> {

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
	protected void handle(CommandHandlerContext<AddOtherHolidayInfoCommand> context) {
		val command = context.getCommand();
		String cid = AppContexts.user().companyId();
		PublicHolidayRemain pubHD = new PublicHolidayRemain(cid, command.getEmployeeId(), command.getPubHdremainNumber());
		publicHolidayRemainRepository.update(pubHD);
		
		ExcessLeaveInfo exLeav = new ExcessLeaveInfo(cid, command.getEmployeeId(), command.getUseAtr(), command.getOccurrenceUnit(), command.getPaymentMethod());
		excessLeaveInfoRepository.update(exLeav);
	}

}
