package nts.uk.ctx.at.record.app.command.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.BusinessType;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author yennth
 * delete business type name command handler
 */
@Stateless
public class DeleteBusinessTypeNameCommandHandler extends CommandHandler<DeleteBusinessTypeNameCommand>{
	@Inject
	private  BusinessTypesRepository businessRepo;
	@Override
	protected void handle (CommandHandlerContext<DeleteBusinessTypeNameCommand> context){
		String companyId = AppContexts.user().companyId();
		Optional<BusinessType> businessTypeOld = businessRepo.findByCode(companyId, context.getCommand().getBusinessTypeCode());
		if(!businessTypeOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		businessRepo.deleteBusinessType(companyId, context.getCommand().getBusinessTypeCode());
	}
}
