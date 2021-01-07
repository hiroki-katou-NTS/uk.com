package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.RegisterHolidayDatesParam;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class RegisterHolDatesCommandHandler extends CommandHandler<RegisterHolidayDatesParam> {
    
    @Inject
    private AbsenceServiceProcess absenceSerivce;

    @Override
    protected void handle(CommandHandlerContext<RegisterHolidayDatesParam> context) {
        RegisterHolidayDatesParam command = context.getCommand();
        String companyID = AppContexts.user().companyId();
        
        Application oldApplication = command.getOldApplication().toDomain();
        Application newApplication = command.getNewApplication().toDomain();
        ApplyForLeave oldApplyForLeave = command.getOriginApplyForLeave().toDomain();
        oldApplyForLeave.setApplication(oldApplication);
        ApplyForLeave newApplyForLeave = command.getNewApplyForLeave().toDomain();
        newApplyForLeave.setApplication(newApplication);
        
        absenceSerivce.registerHolidayDates(
                companyID, 
                newApplyForLeave, 
                oldApplyForLeave, 
                command.getHolidayDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList()), 
                command.getAppAbsenceStartInfoDto().toDomain(companyID));
    }

}
