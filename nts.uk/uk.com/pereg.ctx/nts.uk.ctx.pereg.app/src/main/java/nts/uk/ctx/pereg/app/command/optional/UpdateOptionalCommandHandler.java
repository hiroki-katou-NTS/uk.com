package nts.uk.ctx.pereg.app.command.optional;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;

@Stateless
public class UpdateOptionalCommandHandler extends CommandHandler<PeregUserDefUpdateCommand>
		implements PeregUserDefUpdateCommandHandler {

	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
//	@Inject
//	private PerInfoCtgDataRepository perInfoCtgDataRepository;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
//	@Inject
//	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	protected void handle(CommandHandlerContext<PeregUserDefUpdateCommand> context) {
		val command = context.getCommand();
		if (command.getItems() == null || command.getItems().isEmpty()){
			return;
		}
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(command.getCategoryCd(),companyId);
		
		if (!perInfoCategory.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		
		// In case of person
		if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			
			// Update category data
//			perInfoCtgDataRepository.updateCategoryData(new PerInfoCtgData(command.getRecordId(),perInfoCategory.get().getPersonInfoCategoryId(),command.getPersonId()));
			
			// Insert item data
			PersonInfoItemData itemData = null;
			String recordId = command.getRecordId();
			DataState state = null;
			
			for (ItemValue item : command.getItems()){
				
				state = OptionalUtil.createDataState(item);
				if(state !=null) {
					itemData = new PersonInfoItemData(item.definitionId(), recordId, state);
					perInfoItemDataRepository.registerItemData(itemData);
				}
			}
			
		} else if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			// Update emp category data
//			emInfoCtgDataRepository.updateEmpInfoCtgData(new EmpInfoCtgData(command.getRecordId(),perInfoCategory.get().getPersonInfoCategoryId(),command.getEmployeeId()));
			
			// Add item data
			EmpInfoItemData itemData = null;
			DataState state = null;
			for (ItemValue item : command.getItems()){
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					itemData = new EmpInfoItemData(item.definitionId(), command.getRecordId(), state);
					empInfoItemDataRepository.registerEmpInfoItemData(itemData);
				}
			}
			
		}
	}


}
