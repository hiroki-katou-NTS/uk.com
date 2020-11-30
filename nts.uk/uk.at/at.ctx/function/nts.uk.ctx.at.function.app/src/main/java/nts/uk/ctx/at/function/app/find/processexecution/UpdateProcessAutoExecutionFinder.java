package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.processexecution.dto.SelectedProcessExecutionDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.UpdateProcessAutoExecutionDto;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateProcessAutoExecutionFinder {

	@Inject
	private ProcessExecutionRepository processExecRepo;

	@Inject
	private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

	public List<UpdateProcessAutoExecutionDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.processExecRepo.getProcessExecutionByCompanyId(companyId)
								   .stream()
								   .map(UpdateProcessAutoExecutionDto::createFromDomain)
								   .collect(Collectors.toList());
	}
	
	public SelectedProcessExecutionDto findByCode(String execItemCd) {
		Optional<UpdateProcessAutoExecution> optAutoExec = this.processExecRepo
				.getProcessExecutionByCidAndExecCd(AppContexts.user().companyId(), execItemCd);
		if (optAutoExec.isPresent()) {
			UpdateProcessAutoExecution domain = optAutoExec.get();
			List<WorkplaceInfor> workplaceInfos = this.workplaceConfigInfoAdapter
					.getWorkplaceInforByWkpIds(
							AppContexts.user().companyId(),
							domain.getExecScope().getWorkplaceIdList(),
							domain.getExecScope().getRefDate().orElse(GeneralDate.today()));
			return new SelectedProcessExecutionDto(UpdateProcessAutoExecutionDto.createFromDomain(domain), workplaceInfos);
		}
		return null;
	}
}
