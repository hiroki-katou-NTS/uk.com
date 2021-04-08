package nts.uk.ctx.at.shared.app.command.scherec.addset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AddHolidayAddtionSetCommandHandler extends CommandHandler<AddHolidayAddtionSetCommand> {

    @Inject
    private HolidayAddtionRepository holidayAddtionRepository;

    @Override
    protected void handle(CommandHandlerContext<AddHolidayAddtionSetCommand> context) {

        AddHolidayAddtionSetCommand command = context.getCommand();
        BreakdownTimeDay breakdownTimeDay = new BreakdownTimeDay(
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
