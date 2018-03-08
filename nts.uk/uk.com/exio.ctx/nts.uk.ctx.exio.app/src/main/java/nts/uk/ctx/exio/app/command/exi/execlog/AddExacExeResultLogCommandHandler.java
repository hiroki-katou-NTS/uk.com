package nts.uk.ctx.exio.app.command.exi.execlog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;

@Stateless
@Transactional
public class AddExacExeResultLogCommandHandler extends CommandHandler<ExacExeResultLogCommand>
{
    
    @Inject
    private ExacExeResultLogRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
        ExacExeResultLogCommand addCommand = context.getCommand();
        
        // 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		//
		String userID = AppContexts.user().userId();
		String employeeID = AppContexts.user().userId();
		
        repository.add(ExacExeResultLog.createFromJavaType(0L, companyId, addCommand.getConditionSetCd(), appID, userID, userID, addCommand.getProcessStartDatetime(), addCommand.getStandardAtr(), addCommand.getExecuteForm(), addCommand.getTargetCount(), addCommand.getErrorCount(), addCommand.getFileName(), addCommand.getSystemType(), addCommand.getResultStatus(), addCommand.getProcessEndDatetime(), addCommand.getProcessAtr()));
    
    }
}
