package nts.uk.ctx.exio.app.command.exo.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class UpdateExterOutExecLogUseDeleteFileCommandHandler extends CommandHandlerWithResult<String, Integer> {
	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepo;

	@Override
	protected Integer handle(CommandHandlerContext<String> context) {
		val command = context.getCommand();
		String cid = AppContexts.user().companyId();
		Optional<ExterOutExecLog> exOutExeLog = exterOutExecLogRepo.getExterOutExecLogById(cid, command);
		if (exOutExeLog.isPresent()) {
			ExterOutExecLog domain = exOutExeLog.get();
			domain.setDeleteFile(NotUseAtr.USE);
			exterOutExecLogRepo.update(domain);
			return NotUseAtr.USE.value;
		}
		return NotUseAtr.NOT_USE.value;
	}
}
