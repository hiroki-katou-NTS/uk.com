package nts.uk.ctx.pereg.app.command.optional;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.command.category.DomainValueFactory;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.category.EmpInfoCtgData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;

@Stateless
public class UpdateOptionalCommandHandler extends CommandHandler<PeregUserDefUpdateCommand>
		implements PeregUserDefUpdateCommandHandler {

	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private PerInfoCtgDataRepository perInfoCtgDataRepository;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	protected void handle(CommandHandlerContext<PeregUserDefUpdateCommand> context) {
		val command = context.getCommand();
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategoryByCtgCD(command.getCategoryId(),companyId);
		
		if (!perInfoCategory.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		
		// In case of person
		if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			
			// Update category data
			perInfoCtgDataRepository.updateCategoryData(new PerInfoCtgData(command.getRecordId(),perInfoCategory.get().getPersonInfoCategoryId(),command.getPersonId()));
			
			// Insert item data
			PersonInfoItemData itemData = null;
			String recordId = command.getRecordId();
			DataState state = null;
			
			for (ItemValue item : command.getItems()){
				
				state = createDataState(item);
				
				itemData = new PersonInfoItemData(item.definitionId(), recordId, state);
				perInfoItemDataRepository.updateItemData(itemData);
			}
			
		} else if (perInfoCategory.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			// Update emp category data
			emInfoCtgDataRepository.updateEmpInfoCtgData(new EmpInfoCtgData(command.getRecordId(),perInfoCategory.get().getPersonInfoCategoryId(),command.getEmployeeId()));
			
			// Add item data
			EmpInfoItemData itemData = null;
			DataState state = null;
			for (ItemValue item : command.getItems()){
				state = createDataState(item);
				itemData = new EmpInfoItemData(item.definitionId(), command.getRecordId(), state);
				empInfoItemDataRepository.updateEmpInfoItemData(itemData);
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
