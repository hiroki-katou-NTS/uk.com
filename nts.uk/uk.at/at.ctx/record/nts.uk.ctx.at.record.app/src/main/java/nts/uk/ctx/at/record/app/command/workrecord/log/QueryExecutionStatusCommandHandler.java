package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.find.log.dto.ErrMessageInfoDto;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;

@Stateless
@Transactional
public class QueryExecutionStatusCommandHandler extends CommandHandlerWithResult<ExecutionCommandResult, List<ErrMessageInfoDto>> {
		
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Override
	protected List<ErrMessageInfoDto> handle(CommandHandlerContext<ExecutionCommandResult> context) {
		val command = context.getCommand();
		
		List<ErrMessageInfoDto> result = new ArrayList<ErrMessageInfoDto>();
		
		val errMessageInfos = errMessageInfoRepository.getAllErrMessageInfoByEmpID(command.getEmpCalAndSumExecLogID());
		result = errMessageInfos.stream().map(c -> ErrMessageInfoDto.fromDomain(c)).collect(Collectors.toList());
		
		return result;
	}
}