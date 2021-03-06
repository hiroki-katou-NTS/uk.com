package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.processexecution.dto.UpdateProcessAutoExecutionDto;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProcessExecutionFinder {

	@Inject
	private ProcessExecutionRepository procExecRepo;

	public List<UpdateProcessAutoExecutionDto> findAll() {
		return this.procExecRepo.getProcessExecutionByCompanyId(AppContexts.user().companyId())
				.stream().map(UpdateProcessAutoExecutionDto::createFromDomain).collect(Collectors.toList());
	}
}
