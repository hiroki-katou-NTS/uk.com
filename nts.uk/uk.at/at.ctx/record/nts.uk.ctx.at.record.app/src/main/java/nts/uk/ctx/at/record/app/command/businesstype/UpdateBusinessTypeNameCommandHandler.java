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
 * update business type name command handler
 */
@Stateless
public class UpdateBusinessTypeNameCommandHandler extends CommandHandler<UpdateBusinessTypeNameCommand>{
	@Inject
	private BusinessTypesRepository businessType;
	@Override
	protected void handle (CommandHandlerContext<UpdateBusinessTypeNameCommand> context){
		String companyId = AppContexts.user().companyId();
		
		Optional<BusinessType> businessTypeOld = businessType.findByCode(companyId, context.getCommand().getBusinessTypeCode());
		if(!businessTypeOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		BusinessType businessTypeNew = BusinessType.createFromJavaType(companyId, context.getCommand().getBusinessTypeCode(), context.getCommand().getBusinessTypeName());
		businessType.updateBusinessTypeName(businessTypeNew);
	}
}
