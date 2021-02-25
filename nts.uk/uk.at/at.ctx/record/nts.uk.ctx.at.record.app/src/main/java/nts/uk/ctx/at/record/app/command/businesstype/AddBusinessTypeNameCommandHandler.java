package nts.uk.ctx.at.record.app.command.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.BusinessType;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author yennth
 * Add business type name command handler
 */
@Stateless
public class AddBusinessTypeNameCommandHandler extends CommandHandler<AddBusinessTypeNameCommand>{
	@Inject
	private BusinessTypesRepository businessTypeRep;

	@Override
	protected void handle(CommandHandlerContext<AddBusinessTypeNameCommand> context) {
		String companyId = AppContexts.user().companyId();
		BusinessType businessType = BusinessType.createFromJavaType(companyId, 
																	context.getCommand().getBusinessTypeCode(),
																	context.getCommand().getBusinessTypeName());
		Optional<BusinessType> businessTypeOld = businessTypeRep.findByCode(companyId, context.getCommand().getBusinessTypeCode());
		if(businessTypeOld.isPresent()){
			throw new BusinessException("Msg_3");
		}
		businessTypeRep.insertBusinessType(businessType);
	}
}
