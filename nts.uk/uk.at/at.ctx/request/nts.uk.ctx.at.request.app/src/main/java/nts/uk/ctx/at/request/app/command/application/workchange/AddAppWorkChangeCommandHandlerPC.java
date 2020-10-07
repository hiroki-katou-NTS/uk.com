package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
@Transactional
public class AddAppWorkChangeCommandHandlerPC extends CommandHandlerWithResult<AddAppWorkChangeCommand, ProcessResult> {

    @Inject
    private IWorkChangeRegisterService workChangeRegisterService;
    
    @Override
    protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
        AddAppWorkChangeCommand command = context.getCommand();
        command.getApplicationDto().setEmployeeID(AppContexts.user().employeeId());
        Application application = new Application();
        ApplicationDto applicationDto = command.getApplicationDto();
        if (StringUtils.isBlank(applicationDto.getAppID())) {
            application = Application.createFromNew(
                    EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class), 
                    applicationDto.getEmployeeID(), 
                    EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class), 
                    new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")), 
                    applicationDto.getEnteredPerson(), 
                    applicationDto.getOpStampRequestMode() == null ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(), StampRequestMode.class)), 
                    applicationDto.getOpReversionReason() == null ? Optional.empty() : Optional.ofNullable(new ReasonForReversion(applicationDto.getOpReversionReason())), 
                    applicationDto.getOpAppStartDate() == null ? Optional.empty() : Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                    applicationDto.getOpAppEndDate() == null ? Optional.empty() : Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                    applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.ofNullable(new AppReason(applicationDto.getOpAppReason())), 
                    applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.ofNullable(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())));
        } else {
            application = command.getApplicationDto().toDomain();
        }
        
        
        return workChangeRegisterService.registerProcess(
                command.getMode(),
                command.getCompanyId(),
                application,
                command.getAppWorkChangeDto().toDomain(application),
                command.getHolidayDates() == null ? null : command.getHolidayDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList()),
                command.getIsMail(),
                command.getAppDispInfoStartupDto().toDomain());
        
    }
}
