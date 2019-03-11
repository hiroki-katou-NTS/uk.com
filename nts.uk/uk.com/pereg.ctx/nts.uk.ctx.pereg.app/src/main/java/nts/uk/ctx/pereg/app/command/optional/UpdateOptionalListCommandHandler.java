package nts.uk.ctx.pereg.app.command.optional;

import java.util.ArrayList;
import java.util.List;
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
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefListUpdateCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
@Stateless
public class UpdateOptionalListCommandHandler extends CommandHandler<List<PeregUserDefUpdateCommand>>
implements PeregUserDefListUpdateCommandHandler{
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	protected void handle(CommandHandlerContext<List<PeregUserDefUpdateCommand>> context) {
		val command = context.getCommand();
		
		List<PeregUserDefUpdateCommand> errorLst = new ArrayList<>();
		List<PeregUserDefUpdateCommand> addLst = new ArrayList<>();
		
		command.parallelStream().forEach(c -> {
			if (c.getItems() == null || c.getItems().isEmpty() || c.getCategoryCd().indexOf("CS") > -1) {
				errorLst.add(c);
			} else {
				addLst.add(c);
			}
		});
		
		String cid = AppContexts.user().companyId();

		// Do tất cả nhân viên đều có categoryCd giống nhau nên mình sẽ lấy categoryCd của nhân viên đầu tiên trong list
		Optional<PersonInfoCategory> ctg = perInfoCategoryRepositoty
				.getPerInfoCategoryByCtgCD(command.get(0).getCategoryCd(), cid);
		
		if (!ctg.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		
		// In case of person
		if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			updatePerson(ctg.get(), addLst);
		} else if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			updateEmployee(ctg.get(), addLst);
		}
		
	}
	
	private void updatePerson(PersonInfoCategory ctg, List<PeregUserDefUpdateCommand> addLst) {
		List<PersonInfoItemData> items = new ArrayList<>();
		
		addLst.parallelStream().forEach(c -> {
			c.getItems().parallelStream().forEach(item -> {
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					PersonInfoItemData itemData = new PersonInfoItemData(item.definitionId(), c.getRecordId(), state);
					items.add(itemData);
				}

			});
		});
		
		if(items.size() > 0) {
			perInfoItemDataRepository.updateAll(items);
		}
	
	}
	
	private void updateEmployee(PersonInfoCategory ctg, List<PeregUserDefUpdateCommand> addLst) {
		// Add item data
		List<EmpInfoItemData> items = new ArrayList<>();
		
		addLst.parallelStream().forEach(c -> {
			c.getItems().parallelStream().forEach(item -> {
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					EmpInfoItemData itemData = new EmpInfoItemData(item.definitionId(), c.getRecordId(), state);
					items.add(itemData);
				}
			});
		});
		
		if(items.size() > 0) {
			empInfoItemDataRepository.addAll(items);
		}
		
	}
}
