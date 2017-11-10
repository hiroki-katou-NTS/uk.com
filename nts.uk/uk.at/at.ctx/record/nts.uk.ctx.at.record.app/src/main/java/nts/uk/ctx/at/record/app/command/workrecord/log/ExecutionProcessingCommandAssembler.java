package nts.uk.ctx.at.record.app.command.workrecord.log;

import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;

public interface ExecutionProcessingCommandAssembler {
	EmpCalAndSumExeLog fromDTO(ExecutionProcessingCommand command);
}
