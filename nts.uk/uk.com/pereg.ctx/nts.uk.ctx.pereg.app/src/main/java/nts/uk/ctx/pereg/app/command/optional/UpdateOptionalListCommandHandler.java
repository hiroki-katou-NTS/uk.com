package nts.uk.ctx.pereg.app.command.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		List<String> itemIds = new ArrayList<>();
		command.stream().forEach(c ->{
			c.getItems().forEach(item  ->{
				itemIds.add(item.definitionId());
			});
		});
		List<String> recordIds = new ArrayList<>();
		// do itemId của các employee trong cung một công ty giống nhau  - ta sẽ lấy nhân viên đầu tiên để lấy ra được itemId
		
		command.stream().forEach(c -> {
			if (c.getItems() == null || c.getItems().isEmpty()) {
				errorLst.add(c);
			} else {
				addLst.add(c);
				recordIds.add(c.getRecordId());
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
			List<PersonInfoItemData> itemUpdate = perInfoItemDataRepository.getAllInfoItemByRecordIdsAndItemIds(itemIds.stream().distinct().collect(Collectors.toList()), recordIds);
			updatePerson(ctg.get(), addLst, itemUpdate);
		} else if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			List<EmpInfoItemData> itemUpdate = empInfoItemDataRepository.getAllInfoItemByRecordId(itemIds.stream().distinct().collect(Collectors.toList()), recordIds);
			updateEmployee(ctg.get(), addLst, itemUpdate);
		}
		
	}
	
	private void updatePerson(PersonInfoCategory ctg, List<PeregUserDefUpdateCommand> addLst, List<PersonInfoItemData> itemUpdates) {
		List<PersonInfoItemData> insertLst = new ArrayList<>();
		List<PersonInfoItemData> updateLst = new ArrayList<>();
		
		addLst.stream().forEach(c -> {
			c.getItems().stream().forEach(item -> {
				Optional<PersonInfoItemData> itemUpdateOpt = itemUpdates.stream().filter(insert -> insert.getPerInfoItemDefId().equals(item.definitionId())).findFirst();
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					PersonInfoItemData itemData = new PersonInfoItemData(item.definitionId(), c.getRecordId(), state);
					if(itemUpdateOpt.isPresent()) {
						updateLst.add(itemData);
					}else {
						Optional<PersonInfoItemData> insertOpt = insertLst.stream()
								.filter(insert -> insert.getPerInfoItemDefId().equals(item.definitionId())
										&& insert.getRecordId().equals(c.getRecordId()))
								.findFirst();
						if(!insertOpt.isPresent()) {
							insertLst.add(itemData);
						}
					}
				}

			});
		});
		
		if(updateLst.size() > 0) {
			perInfoItemDataRepository.updateAll(updateLst);
		}
		
		if(insertLst.size() > 0) {
			perInfoItemDataRepository.addAll(insertLst);
		}
	
	}
	
	private void updateEmployee(PersonInfoCategory ctg, List<PeregUserDefUpdateCommand> addLst, List<EmpInfoItemData> itemUpdates) {
		// Add item data
		List<EmpInfoItemData> itemUpdateLst = new ArrayList<>();
		List<EmpInfoItemData> itemInsertLst = new ArrayList<>();
		
		addLst.stream().forEach(c -> {
			c.getItems().stream().forEach(item -> {
				Optional<EmpInfoItemData> itemUpdateOpt = itemUpdates.stream().filter(update -> update.getPerInfoDefId().equals(item.definitionId()) && update.getRecordId().equals(c.getRecordId())).findFirst();
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					EmpInfoItemData itemData = new EmpInfoItemData(item.definitionId(), c.getRecordId(), state);
					if(itemUpdateOpt.isPresent()) {
						itemUpdateLst.add(itemData);
					}else {
						Optional<EmpInfoItemData> insertOpt = itemInsertLst.stream()
								.filter(insert -> insert.getPerInfoDefId().equals(item.definitionId())
										&& insert.getRecordId().equals(c.getRecordId()))
								.findFirst();
						if(!insertOpt.isPresent()) {
							itemInsertLst.add(itemData);
						}
					}
				}
			});
		});
		
		if(itemUpdateLst.size() > 0) {
			empInfoItemDataRepository.updateAll(itemUpdateLst);
		}
		
		if(itemInsertLst.size() > 0) {
			empInfoItemDataRepository.addAll(itemInsertLst);
		}
		
	}
}
