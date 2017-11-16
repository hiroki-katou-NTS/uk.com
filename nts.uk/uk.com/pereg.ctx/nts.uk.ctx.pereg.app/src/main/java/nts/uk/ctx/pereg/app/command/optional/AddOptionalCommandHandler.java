package nts.uk.ctx.pereg.app.command.optional;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.command.category.DomainValueFactory;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommandHandler;

@Stateless
public class AddOptionalCommandHandler extends CommandHandler<PeregUserDefAddCommand>
		implements PeregUserDefAddCommandHandler {
	
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	protected void handle(CommandHandlerContext<PeregUserDefAddCommand> context) {
		val command = context.getCommand();
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(command.getCategoryId(),companyId);
		
		if (!perInfoCategory.isPresent()){
			return;
		}
		
		// In case of person
		if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			
			// Insert category data
//			perInfoCategoryRepositoty.addCategoryData(new PerInfoCtgData(newId,perInfoCategory.get().getPersonInfoCategoryId(),personID));
			
			// Insert item data
			PersonInfoItemData itemData = null;
			String recordId = command.getRecordId();
			DataState state = null;
			
			for (ItemValue item : command.getItems()){
				
				state = createDataState(item);
				
//				itemData = new PersonInfoItemData(perInfoItemDefId, recordId, state);
				perInfoItemDataRepository.addItemData(itemData);
			}
			
		} else if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			// Add emp category data
//			emInfoCtgDataRepository.addCategoryData(new EmpInfoCtgData(newId,perInfoCategory.get().getPersonInfoCategoryId(),empID));
			
			// Add item data
			EmpInfoItemData itemData = null;
			DataState state = null;
			for (ItemValue item : command.getItems()){
				state = createDataState(item);
//				itemData = new EmpInfoItemData(item.getItemDefId(), recordId, state);
				empInfoItemDataRepository.addItemData(itemData);
			}
			
		}
	}
	/**
	 * Create data state from item type
	 * @param item
	 * @return
	 */
	private DataState createDataState(ItemValue item){
		DataState state = null;
		switch(item.itemValueType()){
		case STRING:
			state = DataState.createFromStringValue(DomainValueFactory.convertToString(item.value()));
		break;
		case NUMERIC:
			state = DataState.createFromNumberValue(DomainValueFactory.convertToDecimal(item.value()));
		break;
		case DATE:
			state = DataState.createFromDateValue(DomainValueFactory.convertToDate(item.value()));
		break;
		}
		return state;
	}

}
