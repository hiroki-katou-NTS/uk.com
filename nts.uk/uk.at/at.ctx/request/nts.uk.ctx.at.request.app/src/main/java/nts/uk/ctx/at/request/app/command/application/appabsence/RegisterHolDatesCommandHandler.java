package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.RegisterHolidayDatesParam;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class RegisterHolDatesCommandHandler extends CommandHandlerWithResult<RegisterHolidayDatesParam, ProcessResult> {
    
    @Inject
    private AbsenceServiceProcess absenceSerivce;

    @Override
    protected ProcessResult handle(CommandHandlerContext<RegisterHolidayDatesParam> context) {
        RegisterHolidayDatesParam command = context.getCommand();
        String companyID = AppContexts.user().companyId();
        
        Application oldApplication = command.getOldApplication().toDomain();
        Application newApplication = command.getNewApplication().toDomain();
        newApplication.setAppID(IdentifierUtil.randomUniqueId());
        ApplyForLeave oldApplyForLeave = command.getOriginApplyForLeave().toDomain();
        oldApplyForLeave.setApplication(oldApplication);
        ApplyForLeave newApplyForLeave = command.getNewApplyForLeave().toDomain();
        newApplyForLeave.setApplication(newApplication);
        
        return absenceSerivce.registerHolidayDates(
                companyID, 
                newApplyForLeave, 
                oldApplyForLeave, 
                command.getHolidayDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList()), 
                command.getAppAbsenceStartInfoDto().toDomain(companyID));
    }

}
