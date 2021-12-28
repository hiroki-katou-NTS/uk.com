package nts.uk.ctx.at.shared.app.command.vacation.setting.specialleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattdcal.aggr.vtotalmethod.TimeSpecialLeaveSaveCommandDto;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TimeSpecialLeaveSaveCommandHandler.
 */
@Stateless
public class TimeSpecialLeaveSaveCommandHandler extends CommandHandler<TimeSpecialLeaveSaveCommand> {
	
    @Inject
    private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
    
	@Override
	protected void handle(CommandHandlerContext<TimeSpecialLeaveSaveCommand> context) {
		String companyId = AppContexts.user().companyId();
		TimeSpecialLeaveSaveCommand command = context.getCommand();
		Optional<TimeSpecialLeaveManagementSetting> domain = this.timeSpecialLeaveMngSetRepository.findByCompany(companyId);

        TimeSpecialLeaveManagementSetting setting = command.toDomain(companyId);
        if (domain != null) {
            this.timeSpecialLeaveMngSetRepository.update(setting);
        } else {
            this.timeSpecialLeaveMngSetRepository.add(setting);
        }
    }
	
	public TimeSpecialLeaveSaveCommandDto findByCid() {
		String companyId = AppContexts.user().companyId();
		Optional<TimeSpecialLeaveManagementSetting> domain = this.timeSpecialLeaveMngSetRepository.findByCompany(companyId);
		 return this.converetToDto(domain.get());
	}
	
    private TimeSpecialLeaveSaveCommandDto converetToDto(TimeSpecialLeaveManagementSetting setting) {
    	TimeSpecialLeaveSaveCommandDto dto = new TimeSpecialLeaveSaveCommandDto(null, null);
        dto.setTimeManageType(setting.getTimeVacationDigestUnit().getManage().value);
        dto.setTimeUnit(setting.getTimeVacationDigestUnit().getDigestUnit().value);
        return dto;
    }
}
