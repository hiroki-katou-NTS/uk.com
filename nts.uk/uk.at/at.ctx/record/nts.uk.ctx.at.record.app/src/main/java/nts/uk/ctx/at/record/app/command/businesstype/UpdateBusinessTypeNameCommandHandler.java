package nts.uk.ctx.at.record.app.command.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateBusinessTypeNameCommandHandler extends CommandHandler<UpdateBusinessTypeNameCommand>{
	@Inject
	private BusinessTypesRepository businessType;
	@Override
	protected void handle (CommandHandlerContext<UpdateBusinessTypeNameCommand> context){
		String companyId = AppContexts.user().companyId();
		BusinessType businessTypeNew = BusinessType.createFromJavaType(companyId, context.getCommand().getBusinessTypeCode(), context.getCommand().getBusinessTypeName());
		Optional<BusinessType> businessTypeOld = businessType.findBusinessType(companyId, context.getCommand().getBusinessTypeCode());
		if(businessTypeOld.isPresent()){
			businessType.updateBusinessTypeName(businessTypeNew);
		}
	}
}
