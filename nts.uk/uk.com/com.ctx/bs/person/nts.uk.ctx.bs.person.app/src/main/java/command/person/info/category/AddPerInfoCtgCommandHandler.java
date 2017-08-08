package command.person.info.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class AddPerInfoCtgCommandHandler extends CommandHandler<AddPerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;
	
	@Override
	protected void handle(CommandHandlerContext<AddPerInfoCtgCommand> context) {
		AddPerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		String categoryCode = perInfoCtgRep.getPerInfoCtgCodeLastest(contractCd);
		String ctgNumberCode =  String.valueOf(Integer.parseInt(categoryCode.substring(2, 7)) + 1);
		
		String addZero = "CO";
		for (int i = 5; i > 0; i++) {
			if(i == ctgNumberCode.length()){
				break;
			}
			addZero += "0";
		}
		String newCtgCode = addZero + ctgNumberCode;
		
		List<String> companyIdList = GetListCompanyOfContract.LIST_COMPANY_OF_CONTRACT;
		PersonInfoCategory perInfoCtg = PersonInfoCategory.createFromJavaType(AppContexts.user().companyId(),
				categoryCode, perInfoCtgCommand.getCategoryName().v(), perInfoCtgCommand.getCategoryType().value);
		this.perInfoCtgRep.addPerInfoCtgRoot(perInfoCtg, contractCd);
	}
	
}
