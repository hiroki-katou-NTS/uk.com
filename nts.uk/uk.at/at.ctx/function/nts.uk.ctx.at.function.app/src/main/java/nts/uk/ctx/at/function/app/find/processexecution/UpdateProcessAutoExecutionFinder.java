package nts.uk.ctx.at.function.app.find.processexecution;

import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.UpdateProcessAutoExecutionDto;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateProcessAutoExecutionFinder {

	@Inject
	private ProcessExecutionRepository processExecRepo;

	public List<UpdateProcessAutoExecutionDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.processExecRepo.getProcessExecutionByCompanyId(companyId)
				.stream()
				.map(UpdateProcessAutoExecutionDto::createFromDomain)
				.collect(Collectors.toList());
	}
}
