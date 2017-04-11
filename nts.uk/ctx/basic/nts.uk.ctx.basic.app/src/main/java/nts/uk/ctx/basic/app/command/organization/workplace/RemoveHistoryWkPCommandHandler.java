package nts.uk.ctx.basic.app.command.organization.workplace;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveHistoryWkPCommandHandler extends CommandHandler<String> {

	@Inject
	private WorkPlaceRepository workPlaceRepository;
	// private DepartmentDomainService departmentDomainService;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		// TODO Auto-generated method stub
		String companyCode = AppContexts.user().companyCode();

		if (!workPlaceRepository.isExistHistory(companyCode, context.getCommand().toString())) {
			throw new BusinessException("ER06");
		}
		workPlaceRepository.removeHistory(companyCode, context.getCommand().toString());
		workPlaceRepository.removeMemoByHistId(companyCode, context.getCommand().toString());

	}

}
