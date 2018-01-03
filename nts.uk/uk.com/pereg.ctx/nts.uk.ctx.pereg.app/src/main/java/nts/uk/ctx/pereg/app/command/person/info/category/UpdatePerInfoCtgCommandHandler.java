package nts.uk.ctx.pereg.app.command.person.info.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePerInfoCtgCommandHandler extends CommandHandler<UpdatePerInfoCtgCommand> {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRep;
	
	@Inject
	private EmInfoCtgDataRepository empDataRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoCtgCommand> context) {
		String employeeId = AppContexts.user().employeeId();
		UpdatePerInfoCtgCommand perInfoCtgCommand = context.getCommand();
		
		 List<EmpInfoCtgData> empDataLst = this.empDataRepo.getByEmpIdAndCtgId(employeeId, perInfoCtgCommand.getId());
		if (!this.perInfoCtgRep.checkCtgNameIsUnique(PersonInfoCategory.ROOT_COMPANY_ID,
				perInfoCtgCommand.getCategoryName(), perInfoCtgCommand.getId())) {
			throw new BusinessException(new RawErrorMessage("Msg_215"));
		}
		PersonInfoCategory perInfoCtg = this.perInfoCtgRep
				.getPerInfoCategory(perInfoCtgCommand.getId(), PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.orElse(null);
		if (perInfoCtg == null) {
			return;
		}
		perInfoCtg.setCategoryName(perInfoCtgCommand.getCategoryName());
		perInfoCtg.setCategoryType(perInfoCtgCommand.getCategoryType());
		this.perInfoCtgRep.updatePerInfoCtg(perInfoCtg, PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
	}
}
