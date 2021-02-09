package nts.uk.ctx.exio.app.command.exi.execlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExtExecutionMode;
import nts.uk.ctx.exio.dom.exi.execlog.ExtResultStatus;
import nts.uk.ctx.exio.dom.exi.execlog.ProcessingFlg;
import nts.uk.ctx.exio.dom.exi.execlog.StandardFlg;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddExacExeResultLogCommandHandler extends CommandHandlerWithResult<ExacExeResultLogCommand, String>
{
    
    @Inject
    private ExacExeResultLogRepository repository;
    
    @Override
    protected String handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
        ExacExeResultLogCommand command = context.getCommand();
        
        // 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		//
		String userID = AppContexts.user().userId();
		ExacExeResultLog domain = new ExacExeResultLog(companyId,
				command.getConditionSetCd(),
				appID,
				AppContexts.user().employeeId(),
				userID,
				command.getProcessStartDatetime(),
				EnumAdaptor.valueOf(command.getStandardAtr(), StandardFlg.class),
				EnumAdaptor.valueOf(command.getExecuteForm(), ExtExecutionMode.class),
				command.getTargetCount(),
				command.getErrorCount(),
				command.getFileName(),
				EnumAdaptor.valueOf(command.getSystemType(), SystemType.class),
				Optional.ofNullable(command.getResultStatus() == null ? null : EnumAdaptor.valueOf(command.getResultStatus(), ExtResultStatus.class)),
				Optional.ofNullable(command.getProcessEndDatetime()),
				EnumAdaptor.valueOf(command.getProcessAtr(), ProcessingFlg.class));
        repository.add(domain);
        
        return appID;
    }
}
