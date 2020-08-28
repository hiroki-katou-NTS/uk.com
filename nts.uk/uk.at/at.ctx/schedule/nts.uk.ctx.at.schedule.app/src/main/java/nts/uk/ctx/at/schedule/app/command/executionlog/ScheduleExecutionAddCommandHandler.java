package nts.uk.ctx.at.schedule.app.command.executionlog;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Clone ScheduleExecutionLogAddCommandHandler.
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ScheduleExecutionAddCommandHandler extends CommandHandlerWithResult<ScheduleExecutionAddCommand, ScheduleExecutionLogSaveRespone> {

    /** The create content repository. */
    @Inject
    private ScheduleCreateContentRepository createContentRepository;

    /** The execution log repository. */
    @Inject
    private ScheduleExecutionLogRepository executionLogRepository;

    /** The creator repository. */
    @Inject
    private ScheduleCreatorRepository creatorRepository;

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.
     * app.command.CommandHandlerContext)
     */
    @Override
    protected ScheduleExecutionLogSaveRespone handle(
            CommandHandlerContext<ScheduleExecutionAddCommand> context) {

        ScheduleExecutionLogSaveRespone respone = new ScheduleExecutionLogSaveRespone();

        // get login user
        LoginUserContext loginUserContext = AppContexts.user();

        // get company id
        String companyId = loginUserContext.companyId();

        // auto executionId
        String executionId = IdentifierUtil.randomUniqueId();

        // get command
        ScheduleExecutionAddCommand command = context.getCommand();
        //get employee id
        String employeeId = command.getEmployeeIdLogin();
        if(employeeId==null||employeeId.isEmpty()){
            employeeId = loginUserContext.employeeId();
        }
        // command to domain
        ScheduleExecutionLog domain = command.toDomain(companyId, employeeId, executionId);

        // save domain update add : cid, cd 28/8/2020
        this.executionLogRepository.addNew(domain);
        // save domain update not use memento pattern.
        this.createContentRepository.addNew(command.toDomainContentNew(executionId));

        // save all domain creator update add : cid, cd 28/8/2020
        this.creatorRepository.saveAllNew(command.toDomainCreator(executionId));

        // setup data respone
        respone.setEmployeeId(employeeId);
        respone.setExecutionId(executionId);
        return respone;
    }


}