package nts.uk.ctx.bs.employee.app.command.workplace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class SaveWorkplaceHierarchyCommandHandler extends CommandHandler<SaveWorkplaceHierarchyCommand> {
	
	@Inject
	WorkplaceConfigInfoRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<SaveWorkplaceHierarchyCommand> context) {
		String companyId = AppContexts.user().companyId();
		SaveWorkplaceHierarchyCommand command = context.getCommand();
		List<SaveWorkplaceHierarchyDto> listWorkplaceHierarchyDto = command.getListWorkplaceHierarchyDto();
		if(listWorkplaceHierarchyDto!= null && !listWorkplaceHierarchyDto.isEmpty()) {
			for(SaveWorkplaceHierarchyDto dto:listWorkplaceHierarchyDto) {
				this.repo.updateWorkplaceConfigInfo(companyId, dto.getHistId(), dto.getWorkplaceId(), dto.getHierarchyCode());
			}
		}
	}

}
