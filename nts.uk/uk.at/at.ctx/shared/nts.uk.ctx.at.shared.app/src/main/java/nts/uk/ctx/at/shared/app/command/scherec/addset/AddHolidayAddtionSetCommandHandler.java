package nts.uk.ctx.at.shared.app.command.scherec.addset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;

@Stateless
public class AddHolidayAddtionSetCommandHandler extends CommandHandler<AddHolidayAddtionSetCommand> {

    @Inject
    private HolidayAddtionRepository holidayAddtionRepository;

    @Override
    protected void handle(CommandHandlerContext<AddHolidayAddtionSetCommand> context) {

        AddHolidayAddtionSetCommand command = context.getCommand();
        BreakDownTimeDay breakdownTimeDay = new BreakDownTimeDay(
                new AttendanceTime(command.getOneDay()),
                new AttendanceTime(command.getMorning()),
                new AttendanceTime(command.getAfternoon())
        );
        RefDesForAdditionalTakeLeave setting = new RefDesForAdditionalTakeLeave(
                breakdownTimeDay,
                command.getRefSet(),
                command.getPersonSetRef()
        );

        if(command.getMorning() + command.getAfternoon() > 1440){
            throw new BusinessException("Msg_143");
        }

        holidayAddtionRepository.updateRefForAddTakeLeave(setting);
    }
}
